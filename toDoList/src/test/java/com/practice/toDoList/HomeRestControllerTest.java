	package com.practice.toDoList;

	import com.practice.toDoList.config.TestDataSourceConfig;
	import com.practice.toDoList.config.TestSecurityConfig;
	import lombok.extern.slf4j.Slf4j;
	import org.junit.jupiter.api.AfterEach;
	import org.junit.jupiter.api.Assertions;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.runner.RunWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.core.io.ClassPathResource;
	import org.springframework.core.io.Resource;
	import org.springframework.http.MediaType;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.jdbc.datasource.init.ScriptUtils;
	import org.springframework.security.test.context.support.WithMockUser;
	import org.springframework.test.context.ContextConfiguration;
	import org.springframework.test.context.TestPropertySource;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
	import org.springframework.web.context.WebApplicationContext;

	import java.sql.SQLException;

	import static org.hamcrest.Matchers.hasSize;
	import static org.hamcrest.Matchers.is;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


	@AutoConfigureMockMvc
	@SpringBootTest(classes = {TestSecurityConfig.class,TestDataSourceConfig.class})
	@Slf4j
	@TestPropertySource("/application-test.properties")
	@ComponentScan(basePackages = {"com.practice.toDoList"}) // Adjust the package name
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
		private WebApplicationContext context;
		private final MediaType JSON = MediaType.APPLICATION_JSON;


		@BeforeEach
		public void setupDatabase() throws SQLException {
			Resource schemaScript = new ClassPathResource("schema.sql");
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), schemaScript);

			jdbc.execute(sqlAddTask);

			log.info("Table created");
		}

		@Test
		@WithMockUser(username = "user", roles = "USER")
		public void getTasksHttpRequest() throws Exception {

			 mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(JSON))
				.andExpect(jsonPath("$", hasSize(1)));
		}

		@Test
		@WithMockUser(username ="user", authorities = {"ROLE_USER"})
		public void getTaskByIdHttpRequest() throws Exception{

			mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}",1))
					.andExpect(status().isOk())
					.andExpect(content().contentType(JSON))
					.andExpect(jsonPath("$.id",is(1)))
					.andExpect(jsonPath("$.title",is("Task Test 1")))
					.andExpect(jsonPath("$.description",is("Description Test 1")))
					.andExpect(jsonPath("$.checked",is(1)));

		}
		@AfterEach
		public void cleanUpDatabase() throws SQLException {
			jdbc.execute(sqlDeleteTask);

			Resource schemaScript = new ClassPathResource("DeleteSchema.sql");
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), schemaScript);

		}

	}
