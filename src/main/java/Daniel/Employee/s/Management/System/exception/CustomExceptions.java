package Daniel.Employee.s.Management.System.exception;


public class CustomExceptions {

    // Employee not found
    public static class EmployeeNotFoundException extends RuntimeException {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }

    // employee alredy exist
    public static class EmployeeAlreadyExistsException extends RuntimeException {
        public EmployeeAlreadyExistsException(String message) {
            super(message);
        }
    }
    // under age
    public static class EmployeeUnderageException extends RuntimeException {
        public EmployeeUnderageException(String message) {
            super(message);
        }
    }
    // duplicate email
    public  static  class  EmailAlreadyException extends  RuntimeException{
        public EmailAlreadyException(String message){
            super(message);
        }
    }
}

