-- Exercise 6: Cursors

/*
SCENARIO 1: Generate monthly statements for all customers.

Question: Write a PL/SQL block using an explicit cursor GenerateMonthlyStatements that retrieves all 
transactions for the current month and prints a statement for each customer.

*/


SET SERVEROUTPUT ON;
DECLARE
    CURSOR CUR_MONTHLY_TRANSACTIONS IS
        SELECT C.CUSTOMERID, C.NAME, T.TRANSACTIONDATE, T.AMOUNT, T.TRANSACTIONTYPE
        FROM CUSTOMERS C
        JOIN ACCOUNTS A ON C.CUSTOMERID = A.CUSTOMERID
        JOIN TRANSACTIONS T ON A.ACCOUNTID = T.ACCOUNTID
        WHERE TRUNC(T.TRANSACTIONDATE, 'MM') = TRUNC(SYSDATE, 'MM')
        ORDER BY C.CUSTOMERID, T.TRANSACTIONDATE;
        
    V_CUSTOMER_ID CUSTOMERS.CUSTOMERID%TYPE;
    V_CUSTOMER_NAME CUSTOMERS.NAME%TYPE;
    V_TRANSACTION_DATE TRANSACTIONS.TRANSACTIONDATE%TYPE;
    V_AMOUNT TRANSACTIONS.AMOUNT%TYPE;
    V_TRANSACTION_TYPE TRANSACTIONS.TRANSACTIONTYPE%TYPE;
BEGIN
    OPEN CUR_MONTHLY_TRANSACTIONS;
    
    LOOP
        FETCH CUR_MONTHLY_TRANSACTIONS INTO V_CUSTOMER_ID, V_CUSTOMER_NAME, V_TRANSACTION_DATE, V_AMOUNT, V_TRANSACTION_TYPE;
        EXIT WHEN CUR_MONTHLY_TRANSACTIONS%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || V_CUSTOMER_ID || ', Name: ' || V_CUSTOMER_NAME);
        DBMS_OUTPUT.PUT_LINE('Transaction Date: ' || TO_CHAR(V_TRANSACTION_DATE, 'YYYY-MM-DD') || ', Amount: ' || V_AMOUNT || ', Type: ' || V_TRANSACTION_TYPE);
    END LOOP;
    
    CLOSE CUR_MONTHLY_TRANSACTIONS;
END;
/


/*
SCENARIO 2: Apply annual fee to all accounts.

Question: Write a PL/SQL block using an explicit cursor ApplyAnnualFee that deducts an annual maintenance 
fee from the balance of all accounts.

*/

SET SERVEROUTPUT ON;
DECLARE
    CURSOR CUR_ACCOUNTS IS
        SELECT ACCOUNTID, BALANCE
        FROM ACCOUNTS;
        
    V_ACCOUNT_ID ACCOUNTS.ACCOUNTID%TYPE;
    V_BALANCE ACCOUNTS.BALANCE%TYPE;
    V_ANNUAL_FEE CONSTANT NUMBER := 50; -- Annual fee amount
BEGIN
    OPEN CUR_ACCOUNTS;
    
    LOOP
        FETCH CUR_ACCOUNTS INTO V_ACCOUNT_ID, V_BALANCE;
        EXIT WHEN CUR_ACCOUNTS%NOTFOUND;
        
        UPDATE ACCOUNTS
        SET BALANCE = BALANCE - V_ANNUAL_FEE,
            LASTMODIFIED = SYSDATE
        WHERE ACCOUNTID = V_ACCOUNT_ID;
        
        DBMS_OUTPUT.PUT_LINE('Annual fee of ' || V_ANNUAL_FEE || ' deducted from Account ID: ' || V_ACCOUNT_ID);
    END LOOP;
    
    CLOSE CUR_ACCOUNTS;
    
    COMMIT;
END;
/


/*
SCENARIO 3: Update the interest rate for all loans based on a new policy.

Question: Write a PL/SQL block using an explicit cursor UpdateLoanInterestRates that fetches all loans and 
updates their interest rates based on the new policy.

*/

SET SERVEROUTPUT ON;
DECLARE
    CURSOR CUR_LOANS IS
        SELECT LOANID, INTERESTRATE
        FROM LOANS;
        
    V_LOAN_ID LOANS.LOANID%TYPE;
    V_INTEREST_RATE LOANS.INTERESTRATE%TYPE;
    V_NEW_INTEREST_RATE NUMBER;
    V_NEW_POLICY NUMBER := 2;
    
    FUNCTION CALCULATENEWINTERESTRATE(OLD_RATE NUMBER) RETURN NUMBER IS
    BEGIN
        RETURN OLD_RATE * (1 + (V_NEW_POLICY / 100)); 
    END CALCULATENEWINTERESTRATE;
BEGIN
    OPEN CUR_LOANS;
    
    LOOP
        FETCH CUR_LOANS INTO V_LOAN_ID, V_INTEREST_RATE;
        EXIT WHEN CUR_LOANS%NOTFOUND;
        
        V_NEW_INTEREST_RATE := CALCULATENEWINTERESTRATE(V_INTEREST_RATE);
        
        UPDATE LOANS
        SET INTERESTRATE = V_NEW_INTEREST_RATE
        WHERE LOANID = V_LOAN_ID;
        
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || V_LOAN_ID || ' interest rate updated to ' || V_NEW_INTEREST_RATE);
    END LOOP;
    
    CLOSE CUR_LOANS;
    
    COMMIT;
END;
/
