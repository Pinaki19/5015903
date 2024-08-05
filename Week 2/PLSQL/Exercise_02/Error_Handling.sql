-- Exercise 2: Error Handling

/*
SCENARIO 1: Handle exceptions during fund transfers between accounts.

Question: Write a stored procedure SafeTransferFunds that transfers funds between two accounts. 
Ensure that if any error occurs (e.g., insufficient funds), an appropriate error message is logged 
and the transaction is rolled back.
*/

SELECT * FROM ACCOUNTS;

SET SERVEROUTPUT ON;
CREATE OR REPLACE PROCEDURE SAFETRANSFERFUNDS(
    P_FROM_ACCOUNT_ID IN ACCOUNTS.ACCOUNTID%TYPE,
    P_TO_ACCOUNT_ID IN ACCOUNTS.ACCOUNTID%TYPE,
    P_AMOUNT IN NUMBER
) AS
    V_FROM_BALANCE ACCOUNTS.BALANCE%TYPE;
    V_TO_BALANCE ACCOUNTS.BALANCE%TYPE;
BEGIN
    
    SELECT BALANCE INTO V_FROM_BALANCE
    FROM ACCOUNTS
    WHERE ACCOUNTID = P_FROM_ACCOUNT_ID
    FOR UPDATE;
    
    SELECT BALANCE INTO V_TO_BALANCE
    FROM ACCOUNTS
    WHERE ACCOUNTID = P_TO_ACCOUNT_ID
    FOR UPDATE;
    
    -- Check for sufficient funds
    IF V_FROM_BALANCE < P_AMOUNT THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in the source account.');
    END IF;
    
    -- Perform the transfer
    UPDATE ACCOUNTS
    SET BALANCE = BALANCE - P_AMOUNT,
        LASTMODIFIED = SYSDATE
    WHERE ACCOUNTID = P_FROM_ACCOUNT_ID;
    
    UPDATE ACCOUNTS
    SET BALANCE = BALANCE + P_AMOUNT,
        LASTMODIFIED = SYSDATE
    WHERE ACCOUNTID = P_TO_ACCOUNT_ID;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer successful.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: ' || SQLERRM);
END SAFETRANSFERFUNDS;
/

EXEC SAFETRANSFERFUNDS(2,1,500);
SELECT * FROM ACCOUNTS;


/*
SCENARIO 2: Manage errors when updating employee salaries.

Question: Write a stored procedure UpdateSalary that increases the salary of an employee by a given percentage. 
If the employee ID does not exist, handle the exception and log an error message.

*/

SELECT * FROM EMPLOYEES;

SET SERVEROUTPUT ON;
CREATE OR REPLACE PROCEDURE UPDATESALARY(
    P_EMPLOYEE_ID IN EMPLOYEES.EMPLOYEEID%TYPE,
    P_PERCENTAGE IN NUMBER
) AS
    V_OLD_SALARY EMPLOYEES.SALARY%TYPE;
BEGIN
    -- Fetch the current salary
    SELECT SALARY INTO V_OLD_SALARY
    FROM EMPLOYEES
    WHERE EMPLOYEEID = P_EMPLOYEE_ID;

    -- Update the salary
    UPDATE EMPLOYEES
    SET SALARY = SALARY * (1 + P_PERCENTAGE / 100),
        HIREDATE = SYSDATE
    WHERE EMPLOYEEID = P_EMPLOYEE_ID;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully.');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || P_EMPLOYEE_ID || ' does not exist.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Salary update failed: ' || SQLERRM);
END UPDATESALARY;
/

EXEC UPDATESALARY(1,5);
EXEC UPDATESALARY(2,3);

SELECT * FROM EMPLOYEES;


/*
SCENARIO 3: Ensure data integrity when adding a new customer.

Question: Write a stored procedure AddNewCustomer that inserts a new customer into the Customers table. 
If a customer with the same ID already exists, handle the exception by logging an error and preventing 
the insertion.
*/

SELECT * FROM CUSTOMERS;

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE ADDNEWCUSTOMER(
    P_CUSTOMER_ID IN CUSTOMERS.CUSTOMERID%TYPE,
    P_NAME IN CUSTOMERS.NAME%TYPE,
    P_DOB IN CUSTOMERS.DOB%TYPE,
    P_BALANCE IN CUSTOMERS.BALANCE%TYPE
) AS
BEGIN
    -- Attempt to insert the new customer
    DBMS_OUTPUT.PUT_LINE('INSERTING...');
    DBMS_OUTPUT.PUT_LINE('CUSTOMER_ID : ' || P_CUSTOMER_ID);
    DBMS_OUTPUT.PUT_LINE('NAME : ' || P_NAME);
    DBMS_OUTPUT.PUT_LINE('DOB : ' || P_DOB);
    DBMS_OUTPUT.PUT_LINE('BALANCE : ' || P_BALANCE);
    
    INSERT INTO CUSTOMERS (CUSTOMERID, NAME, DOB, BALANCE, LASTMODIFIED)
    VALUES (P_CUSTOMER_ID, P_NAME, TO_DATE(P_DOB,'YYYY-MM-DD'), P_BALANCE, SYSDATE);
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || P_CUSTOMER_ID || ' already exists.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Customer addition failed: ' || SQLERRM);
END ADDNEWCUSTOMER;
/

EXEC ADDNEWCUSTOMER(3,'PINAKI BANERJEE',TO_DATE('21-10-2002','DD-MM-YYYY'),50000);

SELECT * FROM CUSTOMERS;
