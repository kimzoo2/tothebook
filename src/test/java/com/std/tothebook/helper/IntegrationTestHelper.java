package com.std.tothebook.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.std.tothebook.controller.api.MyBookController;
import com.std.tothebook.service.MyBookService;

@WebMvcTest(controllers = {
	MyBookController.class
})
public abstract class IntegrationTestHelper {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected MyBookService myBookService;

}
