	package com.practice.toDoList;


	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.practice.toDoList.entity.Task;
	import com.practice.toDoList.repository.TaskRepository;
	import lombok.extern.slf4j.Slf4j;
	import org.junit.jupiter.api.*;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.http.MediaType;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.security.test.context.support.WithMockUser;
	import org.springframework.test.context.TestPropertySource;
	import org.springframework.test.context.junit.jupiter.SpringExtension;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

	import java.sql.SQLException;

	import static org.hamcrest.Matchers.hasSize;
	import static org.hamcrest.Matchers.is;
	import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


	@AutoConfigureMockMvc
	@SpringBootTest
	@Slf4j
	@TestPropertySource("/application-test.properties")
	@ExtendWith(SpringExtension.class)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class HomeRestControllerTest{

		@Value("${sql.script.create.task}")
		private String sqlAddTask;

		@Value("${sql.script.delete.task}")
		private String sqlDeleteTask;

		@Autowired
		private  JdbcTemplate jdbc;

		@Autowired
		private MockMvc mockMvc;

		@Autowired
		private TaskRepository taskRepository;

		private static final ObjectMapper objectMapper = new ObjectMapper();

		private final MediaType JSON = MediaType.APPLICATION_JSON;


		@BeforeEach
		public void setupDatabase() throws SQLException {

			jdbc.execute(sqlAddTask);

			log.info("->>>> Data inserted");
		}

		@Test
		@WithMockUser
		@Order(1)
		public void getTasksHttpRequest() throws Exception {
			log.info("->>getTasks :" +taskRepository.findAll().toString());

			 mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(JSON))
				.andExpect(jsonPath("$", hasSize(1)));
		}

		@Test
		@WithMockUser
		@Order(2)
		public void getTaskByIdHttpRequest() throws Exception{

			log.info("->>getTaskById:" +taskRepository.findAll().toString());

			mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}",2))
					.andExpect(status().isOk())
					.andExpect(content().contentType(JSON))
					.andExpect(jsonPath("$.id",is(2)))
					.andExpect(jsonPath("$.title",is("Task Test 1")))
					.andExpect(jsonPath("$.description",is("Description Test 1")))
					.andExpect(jsonPath("$.checked",is(true)));

		}

		@Test
		@WithMockUser
		@Order(3)
		public void getTaskByIdHttpRequestEmptyResponse() throws Exception{
			log.info("->>getTaskByIdEmpty:" +taskRepository.findAll().toString());

			mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}",0))
					.andExpect(status().is4xxClientError())
					.andExpect(content().contentType(JSON))
					.andExpect(jsonPath("$.status",is("NOT_FOUND")))
					.andExpect(jsonPath("$.message",is("Task id not found 0")));

		}

		@Test
		@WithMockUser
		@Order(4)
		public void createTaskHttpRequest() throws  Exception{

			log.info("->>create:" +taskRepository.findAll().toString());

			Task testTask = new Task("My task", "Description of my task",false);

			mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks").with(csrf().asHeader())
					.contentType(JSON)
					.content(objectMapper.writeValueAsString(testTask)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.title",is("My task")))
					.andExpect(jsonPath("$.description",is("Description of my task")))
					.andExpect(jsonPath("$.checked",is(false)));

		}

		@Test
		@WithMockUser
		@Order(5)
		public void deleteTaskHttpRequest() throws  Exception {
			log.info("->>delete:" +taskRepository.findAll().toString());
			Assertions.assertTrue(taskRepository.findById(6).isPresent());
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/{id}",6).with(csrf().asHeader()))
					.andExpect(status().isOk());

					Assertions.assertFalse(taskRepository.findById(6).isPresent());

		}

		@Test
		@WithMockUser
		@Order(6)
		public void updateTaskHttpRequest() throws Exception{
			log.info("->>update:" +taskRepository.findAll().toString());

			mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks").with(csrf().asHeader())
							.contentType(JSON)
							.content(objectMapper.writeValueAsString(new Task(7,"Task Test modified","Description Test modified",false))))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id",is(7)))
					.andExpect(jsonPath("$.title",is("Task Test modified")))
					.andExpect(jsonPath("$.description",is("Description Test modified")))
					.andExpect(jsonPath("$.checked",is(false)));		}
			@AfterEach
		public void cleanUpDatabase() throws SQLException {
			jdbc.execute(sqlDeleteTask);

			log.info("->>>> Data deleted");

		}

	}
