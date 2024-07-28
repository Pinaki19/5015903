import java.util.Scanner;

// Define the Task class with attributes
class Task {
    int taskId;
    String taskName;
    String status;

    // Constructor
    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task < TaskId: " + taskId + ", TaskName: " + taskName + ", Status: " + status +" >";
    }
}

// Node class for the linked list
class Node {
    Task task;
    Node next;

    // Constructor
    public Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

// SinglyLinkedList class to manage tasks
// keeping track of only the head will make insertion O(n) 
// rather storing the tail also will make insertion at the end to O(1)
class SinglyLinkedList {
    private Node head;
    private Node tail; // Added tail pointer

    // Constructor
    public SinglyLinkedList() {
        head = null;    // Initialize head
        tail = null;    // Initialize tail
    }

    // Method to add a task to the list
    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            tail = newNode; // Set tail when list is empty
        } else {
            tail.next = newNode; // Use tail to add node
            tail = newNode; // Update tail to new node
        }
    }

    // Method to search for a task by taskId
    public Task searchTask(int taskId) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.taskId == taskId) {
                return temp.task;
            }
            temp = temp.next;
        }
        return null; // Task not found
    }

    // Method to traverse and print the list
    public void traverse() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.task);
            temp = temp.next;
        }
    }

    // Method to delete a task by taskId
    public void deleteTask(int taskId) {
        if (head == null) {
            return; // List is empty
        }

        if (head.task.taskId == taskId) {
            head = head.next; // Remove the head
            if (head == null) {
                tail = null; // If the list becomes empty, update tail
            }
            return;
        }

        Node temp = head;
        while (temp.next != null && temp.next.task.taskId != taskId) {
            temp = temp.next;
        }

        if (temp.next != null) {
            temp.next = temp.next.next; // Remove the node
            if (temp.next == null) {
                tail = temp; // Update tail if the last node was removed
            }
        }
    }
}

// Test the implementation
public class TaskManager {
    public static void main(String[] args) {
        SinglyLinkedList taskList = new SinglyLinkedList();

        // Adding tasks based on hard-coded values
        taskList.addTask(new Task(1, "Task 1", "Incomplete"));
        taskList.addTask(new Task(2, "Task 2", "Complete"));
        taskList.addTask(new Task(3, "Task 3", "Incomplete"));
        taskList.addTask(new Task(4, "Task 4", "Complete"));

        // Traverse the list
        System.out.println("\nTasks in the list:");
        taskList.traverse();

        // Search for a task
        int searchId = 2;
        System.out.println("\nSearching for task ID " + searchId + ":");
        Task foundTask = taskList.searchTask(searchId);
        if (foundTask != null) {
            System.out.println("Found Task: " + foundTask);
        } else {
            System.out.println("Task not found.");
        }

        // Delete a task
        int deleteId = 3;
        System.out.println("\nDeleting task ID " + deleteId + ":");
        taskList.deleteTask(deleteId);

        // Traverse the list again
        System.out.println("\nTasks in the list after deletion:");
        taskList.traverse();
    }
}
