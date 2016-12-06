# ClaimStatus

RESTful API to receive an HL7 claim status inquiry and return an acknowledgement.
The acknowledgement simply states that the request was received, we will handle 
any actual processing in a separate asynchronous thread.
