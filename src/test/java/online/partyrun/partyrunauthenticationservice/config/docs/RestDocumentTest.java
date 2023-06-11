package online.partyrun.partyrunauthenticationservice.config.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static online.partyrun.partyrunauthenticationservice.config.docs.ApiDocumentUtils.getDocumentRequest;
import static online.partyrun.partyrunauthenticationservice.config.docs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
@WebMvcTest
public abstract class RestDocumentTest {

    @Autowired private ObjectMapper objectMapper;
    protected MockMvc mockMvc;

    protected String toRequestBody(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    @BeforeEach
    public void setupMockMvc(
            WebApplicationContext ctx,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(ctx)
                        .apply(
                                documentationConfiguration(restDocumentationContextProvider)
                                        .uris()
                                        .withScheme("https")
                                        .withHost("partyrun.online")
                                        .withPort(0))
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .alwaysDo(print())
                        .alwaysDo(document("api/"))
                        .build();
    }

    protected void setPrintDocs(ResultActions actions, String title) {
        try {
            actions.andDo(print())
                    .andDo(
                            document(
                                    title,
                                    getDocumentRequest(),
                                    getDocumentResponse()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}