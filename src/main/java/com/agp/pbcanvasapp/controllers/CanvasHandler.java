package com.agp.pbcanvasapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sdk.canvas.CanvasRequest;
import sdk.canvas.SignedRequest;

/**
 * handles the Signed request sent by Salesforce canvas app
 */
@Controller
public class CanvasHandler {

    @Value("salesforce.client_secret")
    private String secret;

    //read signed request from http request body
    @RequestMapping(value = "/sfdc/auth", method = RequestMethod.POST)
    public ResponseEntity<String> getSignedRequest(@RequestBody String signature) {

        if (signature != null && SignedRequest.verifyAndDecode(signature, secret) != null) {
            CanvasRequest verifiedSignature = SignedRequest.verifyAndDecode(signature, secret);
            return new ResponseEntity<String>(verifiedSignature.toString(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Request not made by salesforce or bad Signed Request", HttpStatus.FORBIDDEN);
        }

    }

}
