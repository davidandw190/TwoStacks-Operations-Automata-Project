package org.example;

import java.util.Stack;
import java.util.Scanner;

public class FLAT_TwoStacks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your expression: ");
        String expression = scanner.nextLine();
        System.out.println("\n--------------------Solution----------------------\n");
        scanner.close();

        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ') {
                continue;
            } else if (Character.isDigit(ch)) {
                double num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + Character.getNumericValue(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(num);
                System.out.println("Pushing " + num + " to the operands stack\n");
            } else if (ch == '(') {
                operators.push(ch);
                System.out.println("Pushing " + ch + " to the operators stack\n");
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    double val2 = operands.pop();
                    double val1 = operands.pop();
                    char op = operators.pop();
                    double result = applyOp(val1, val2, op);
                    operands.push(result);
                    System.out.println("Popping " + val2 + " from operands stack, popping " + val1 + " from operands stack, popping " + op + " from operators stack, applying " + val1 + " " + op + " " + val2 + " = " + result + ", and pushing " + result + " to operands stack");
                }
                operators.pop();
                System.out.println("Popping " + ch + " from operators stack\n");
            } else if (ch == '&' || ch == '|') {
                operators.push(ch);
                System.out.println("Pushing " + ch + " to the operators stack\n");
            } else {
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    double val2 = operands.pop();
                    double val1 = operands.pop();
                    char op = operators.pop();
                    double result = applyOp(val1, val2, op);
                    operands.push(result);
                    System.out.println("Popping " + val2 + " from operands stack, popping " + val1 + " from operands stack, popping " + op + " from operators stack, applying " + val1 + " " + op + " " + val2 + " = " + result + ", and pushing " + result + " to the operands stack\n");
                }
                operators.push(ch);
                System.out.println("Pushing " + ch + " to the operators stack\n");
            }

            System.out.println(" # OPERATORS STACK: "+operators);
            System.out.println(" # OPERANDS STACK: "+operands);
            System.out.println("\n===============================================\n");
        }

        while (!operators.isEmpty()) {
            double val2 = operands.pop();
            double val1 = operands.pop();
            char op = operators.pop();
            double result = applyOp(val1, val2, op);
            operands.push(result);
            System.out.println("Popping " + val2 + " from operands stack, popping " + val1 + " from operands stack, " +
                    "popping " + op + " from operators stack, applying " + val1 + " " + op + " " + val2 + " = " + result
                    + ", and pushing " + result + " to operands stack\n");
        }

        System.out.println(" # OPERATORS STACK: "+operators);
        System.out.println(" # OPERANDS STACK: "+operands);
        System.out.println("\n========================================\nYour result = " + operands.pop());
    }

    public static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    public static double applyOp(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Huh..Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}

