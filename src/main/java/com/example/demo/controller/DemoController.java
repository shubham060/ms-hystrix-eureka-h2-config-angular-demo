package com.example.demo.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.demo.model.EmptyJSONResponse;
import com.example.demo.model.LoginModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.DemoDelegateService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DemoController {

	Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	DemoDelegateService demoDelegateService;

	@PostMapping("/authenticate")
	public ResponseEntity<UserModel> authenticateUser(@RequestBody LoginModel loginModel) {

		logger.info("Entering authenticateUser method of DemoController Class {}", loginModel);

		try {
			ResponseEntity<UserModel> res = demoDelegateService.callDataBaseService(HttpMethod.POST, loginModel,
					"authenticatefromdb", UserModel.class);

			logger.info("Exiting authenticateUser method of DemoController Class {}", res.getBody());

			if (res.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (RestClientException e) {
			e.printStackTrace();

			logger.error(e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/users")
	public ResponseEntity<ArrayList> getAllUsers() {

		logger.info("Entering getAllUsers method of DemoController Class");
		try {
			ResponseEntity<ArrayList> list = demoDelegateService.callDataBaseService(HttpMethod.GET, null,
					"getAllUsers", ArrayList.class);
			logger.info("Exiting getAllUsers method of DemoController Class {}", list.getBody());
			if (list.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(list.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserModel> getUserById(@PathVariable("id") long id) {

		logger.info("Entering getUserById method of DemoController Class  {}", id);
		try {
			ResponseEntity<UserModel> res = demoDelegateService.callDataBaseService(HttpMethod.GET, null, "users/" + id,
					UserModel.class);
			logger.info("Exiting getUserById method of DemoController Class {} ", res.getBody());
			if (res.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/users")
	public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {

		logger.info("Entering createUser method of DemoController Class {} ", user);
		try {
			ResponseEntity<String> res = demoDelegateService.callDataBaseService(HttpMethod.POST, user, "users",
					String.class);
			logger.info("Exiting createUser method of DemoController Class {} ", res.getBody());
			if (res.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserModel> updateUser(@PathVariable("id") long id, @RequestBody UserModel user) {

		logger.info("Entering updateUser method of DemoController Class {}  {} ", id, user);
		try {
			ResponseEntity<UserModel> res = demoDelegateService.callDataBaseService(HttpMethod.PUT, user, "users/" + id,
					UserModel.class);
			logger.info("Exiting updateUser method of DemoController Class {} ", res.getBody());
			if (res.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") int id) {

		logger.info("Entering deleteUser method of DemoController Class {} ", id);
		try {
			ResponseEntity<String> res = demoDelegateService.callDataBaseService(HttpMethod.DELETE, null, "users/" + id,
					String.class);
			logger.info("Exiting deleteUser method of DemoController Class {} ", res.getBody());
			if (res.getStatusCode() == HttpStatus.NO_CONTENT) {
				return new ResponseEntity<>(new EmptyJSONResponse(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("users/forget")
	public ResponseEntity<Object> forgetPassword(@RequestBody UserModel userModel) {

		logger.info("Entering forgetPassword method of DemoController Class {} ", userModel);
		try {
			ResponseEntity<Object> res = demoDelegateService.callDataBaseService(HttpMethod.POST, userModel,
					"users/forget", Object.class);
			logger.info("Exiting forgetPassword method of DemoController Class {} ", res.getBody());
			if (res.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(new EmptyJSONResponse(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
