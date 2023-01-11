package com.example.auth.exceptions;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.auth.utils.Constant;

public class CustomError {

	private String status;
	
	private Integer statusCode;
    
	private Object path;
	
    private Instant timestamp;
    
    private String message;
    
    private List<?> errors;
	    

    public CustomError(BuilderError builderError) {
    	this.status 	= builderError.status;
    	this.statusCode	= builderError.statusCode;
    	this.path		= builderError.path;
    	this.timestamp	= builderError.timestamp == null ? this.timestamp() : builderError.timestamp;
    	this.message	= builderError.message 	 == null ? 	"" 			    : builderError.message;
    	this.errors 	= builderError.errors;
    }
    
    private Instant timestamp() {
        Duration threeHours = Duration.ofHours(3);    	
        return  Instant.now().plus( threeHours.negated() );
    }
    
   
    public String getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public Object getPath() {
		return path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public List<?> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "CustomError [status=" + status + ", statusCode=" + statusCode + ", path=" + path + ", timestamp="
				+ timestamp + ", message=" + message + ", errors=" + errors + "]";
	}


    public static class BuilderError {
    	private String status;
    	
    	private Integer statusCode;
        
    	private Object path;
    	        
        private Instant timestamp;

        private String message;
        
        private List<?> errors;
        
        public BuilderError status(HttpStatus status) {
        	this.status = status.name();
        	return this;
        }
        
        public BuilderError statusCode(HttpStatus statusCode) {
        	this.statusCode = statusCode.value();
        	return this;
        }
        
        public BuilderError path( Object object) {
        	
        	if(object instanceof WebRequest ) {
        		WebRequest webRequest = ( WebRequest ) object;
        		object = webRequest.getDescription(false).substring(Constant.URI_PATH.length());
        	}        	
                	
        	this.path = object;
        	return this;
        }
        
        public BuilderError timestamp(Instant timestamp) {
        	this.timestamp = timestamp;
        	return this;
        }
        
        public BuilderError message( String message) {
        	this.message = message;
        	return this;
        }
        
        public BuilderError errors(List<?> errors) {
        	this.errors = errors;
        	return this;
        }
    
        public CustomError build() {
        	return new CustomError(this);
        }
        
    }
   
}
