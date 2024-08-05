-- Exercise 4: Functions

/*
SCENARIO 1: Calculate the age of customers for eligibility checks.

Question: Write a function CalculateAge that takes a customer's date of birth as input and 
returns their age in years.

*/

SELECT * FROM CUSTOMERS;

SET SERVEROUTPUT ON;
CREATE OR REPLACE FUNCTION CALCULATEAGE(
    P_DOB IN DATE
) RETURN NUMBER IS
    V_AGE NUMBER;
BEGIN
    V_AGE := ROUND((TO_DATE(SYSDATE,'DD-MM-YYYY') - TO_DATE(P_DOB,'DD-MM-YYYY')) / 365);
    IF V_AGE<0 THEN
        V_AGE := V_AGE+100;
    END IF;
    RETURN V_AGE;
END CALCULATEAGE;
/

DECLARE
    CURSOR CURSOR_CUST IS SELECT * FROM CUSTOMERS;
    V_CUSTOMER CUSTOMERS%ROWTYPE;
    V_AGE NUMBER;
BEGIN
    OPEN CURSOR_CUST;
    LOOP
        FETCH CURSOR_CUST INTO V_CUSTOMER;
        EXIT WHEN CURSOR_CUST%NOTFOUND;
        
        V_AGE := CALCULATEAGE(V_CUSTOMER.DOB);
        
        DBMS_OUTPUT.PUT_LINE('CUSTOMER ID : ' || V_CUSTOMER.CUSTOMERID || ' AGE : ' || V_AGE);
    END LOOP;
    CLOSE CURSOR_CUST;
END;
/


/*
SCENARIO 2: The bank needs to compute the monthly installment for a loan.

Question: Write a function CalculateMonthlyInstallment that takes the loan amount, interest rate, 
and loan duration in years as input and returns the monthly installment amount.
*/

SELECT * FROM LOANS;

SET SERVEROUTPUT ON;
CREATE OR REPLACE FUNCTION CALCULATEMONTHLYINSTALLMENT(
    P_LOAN_AMOUNT IN NUMBER,
    P_INTEREST_RATE IN NUMBER,
    P_LOAN_DURATION_YEARS IN NUMBER
) RETURN NUMBER IS
    V_MONTHLY_RATE NUMBER;
    V_NUM_PAYMENTS NUMBER;
    V_MONTHLY_INSTALLMENT NUMBER;
BEGIN
    V_MONTHLY_RATE := P_INTEREST_RATE / 12 / 100;
    V_NUM_PAYMENTS := P_LOAN_DURATION_YEARS * 12;
    IF V_MONTHLY_RATE = 0 THEN
        V_MONTHLY_INSTALLMENT := P_LOAN_AMOUNT / V_NUM_PAYMENTS;
    ELSE
        V_MONTHLY_INSTALLMENT := P_LOAN_AMOUNT * V_MONTHLY_RATE / (1 - POWER(1 + V_MONTHLY_RATE, -V_NUM_PAYMENTS));
    END IF;
    RETURN V_MONTHLY_INSTALLMENT;
END CALCULATEMONTHLYINSTALLMENT;
/

SET SERVEROUTPUT ON;
DECLARE
    CURSOR LOAN_CUR IS SELECT * FROM LOANS;
    V_DATA LOANS%ROWTYPE;
    V_DURATION NUMBER;
    V_MONTHLYINSTALLMENT NUMBER;
BEGIN
    OPEN LOAN_CUR;
    LOOP
        FETCH LOAN_CUR INTO V_DATA;
        EXIT WHEN LOAN_CUR%NOTFOUND;
           
        V_DURATION := TRUNC((V_DATA.ENDDATE - V_DATA.STARTDATE)/365);
        V_MONTHLYINSTALLMENT :=  TRUNC(CALCULATEMONTHLYINSTALLMENT(V_DATA.LOANAMOUNT, V_DATA.INTERESTRATE, V_DURATION),2);
        DBMS_OUTPUT.PUT_LINE('CUSTOMER ID : ' || V_DATA.CUSTOMERID || ' MONTHLY INSTALLAMENT : ' || V_MONTHLYINSTALLMENT);
        
    END LOOP;
    CLOSE LOAN_CUR;
END;
/
        
/*
SCENARIO 3: Check if a customer has sufficient balance before making a transaction.

Question: Write a function HasSufficientBalance that takes an account ID and an amount as input and 
returns a boolean indicating whether the account has at least the specified amount.

*/

SELECT * FROM ACCOUNTS;

SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION HASSUFFICIENTBALANCE(
    P_ACCOUNT_ID IN ACCOUNTS.ACCOUNTID%TYPE,
    P_AMOUNT IN NUMBER
) RETURN BOOLEAN IS
    V_BALANCE ACCOUNTS.BALANCE%TYPE;
BEGIN
    SELECT BALANCE INTO V_BALANCE
    FROM ACCOUNTS
    WHERE ACCOUNTID = P_ACCOUNT_ID;

    RETURN V_BALANCE >= P_AMOUNT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Error checking balance: ' || SQLERRM);
END HASSUFFICIENTBALANCE;
/

SET SERVEROUTPUT ON;
DECLARE
    V_ACCOUNTID ACCOUNTS.ACCOUNTID%TYPE := &ACCOUNTID;
    V_AMOUNT NUMBER := &AMOUNT;
    V_HAS BOOLEAN;
BEGIN
    V_HAS  := HASSUFFICIENTBALANCE(V_ACCOUNTID, V_AMOUNT);
    IF V_HAS = TRUE THEN DBMS_OUTPUT.PUT_LINE(V_ACCOUNTID || ' HAS SUFFICIENT AMOUNT');
    ELSE DBMS_OUTPUT.PUT_LINE(V_ACCOUNTID || ' DOES NOT HAVE SUFFICIENT AMOUNT');
    END IF;
END;
/