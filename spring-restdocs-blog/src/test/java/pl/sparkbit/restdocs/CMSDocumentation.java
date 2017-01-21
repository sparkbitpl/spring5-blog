package pl.sparkbit.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.sparkbit.restdocs.domain.Document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RestdocsDemoApplication.class)
public class CMSDocumentation {

	@Rule
	public JUnitRestDocumentation restDocumentation =
			new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
			.apply(documentationConfiguration(this.restDocumentation))
			.build();
	}

	@Test
	public void responseCodeTest() throws Exception {
		this.mockMvc.perform(
			get("/cms/status")
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andDo(
			document("status")
		);
	}

	@Test
	public void retrieveDocumentTest() throws Exception {
		this.mockMvc.perform(get("/cms/document/{id}", 1L))
			.andExpect(status().isOk())
			.andDo(document("retrieveDocument",
				pathParameters(
					parameterWithName("id").description("Document's id")
				),
				responseFields(
					fieldWithPath("author").description("Document's author"),
					fieldWithPath("title").description("Document's title")
				)
			));
	}

	@Test
	public void createDocumentTest() throws Exception {
		Document document = new Document("Jack Tester", "Testing REST APIs");

		this.mockMvc.perform(
			post("/cms/document")
					.content(objectMapper.writeValueAsString(document))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(document("createDocument",
					responseFields(
							fieldWithPath("id").description("Created document's id"))
			));
	}

}
