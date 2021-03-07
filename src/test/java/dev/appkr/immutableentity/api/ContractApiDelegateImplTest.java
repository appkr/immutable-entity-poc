package dev.appkr.immutableentity.api;

import dev.appkr.immutableentity.api.error.ExceptionTranslator;
import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.ContractStatusDto;
import dev.appkr.immutableentity.config.Constants;
import dev.appkr.immutableentity.domain.Contract;
import dev.appkr.immutableentity.repository.ContractRepository;
import dev.appkr.immutableentity.service.ContractService;
import dev.appkr.immutableentity.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.web.filter.CharacterEncodingFilter;

import static dev.appkr.immutableentity.Fixtures.aContract;
import static dev.appkr.immutableentity.Fixtures.aContractDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ContractApiDelegateImplTest {

  private MockMvc mvc;

  @Autowired ContractApiDelegate apiDelegate;
  @Autowired ExceptionTranslator exceptionTranslator;
  @Autowired MappingJackson2HttpMessageConverter messageConverter;
  @Autowired @Qualifier("defaultValidator") Validator validator;
  @MockBean ContractService mockContractService;

  @Autowired ContractRepository repository;

  @Test
  public void testCreateContract() throws Exception {
    ContractDto dto = aContractDto();
    when(mockContractService.createContract(dto)).thenReturn(dto);

    final ResultActions res = this.mvc.perform(
        post("/api/contracts")
            .contentType(Constants.V1_MEDIA_TYPE)
            .content(TestUtils.convertObjectToString(dto))
            .characterEncoding("utf-8")
    ).andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  public void testUpdateContract() throws Exception {
    Contract entity = aContract().build();
    repository.saveAndFlush(entity);

    ContractDto dto = aContractDto().status(ContractStatusDto.EFFECTIVE);
    when(mockContractService.updateContract(entity.getExternalId(), dto)).thenReturn(dto);

    final ResultActions res = this.mvc.perform(
        put("/api/contracts/{contractId}", entity.getExternalId())
            .contentType(Constants.V1_MEDIA_TYPE)
            .content(TestUtils.convertObjectToString(dto))
            .characterEncoding("utf-8")
    ).andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  public void testGetContract() throws Exception {
    Contract entity = aContract().build();
    repository.saveAndFlush(entity);

    ContractDto dto = aContractDto();
    when(mockContractService.getContract(entity.getExternalId())).thenReturn(dto);

    final ResultActions res = this.mvc.perform(
        get("/api/contracts/{contractId}", entity.getExternalId())
            .characterEncoding("utf-8")
    ).andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @BeforeEach
  public void setup() {
    ContractApiController controller = new ContractApiController(apiDelegate);
    this.mvc = MockMvcBuilders.standaloneSetup(controller)
        .addFilters(new CharacterEncodingFilter("utf-8", true))
        .setControllerAdvice(exceptionTranslator)
        .setConversionService(TestUtils.createFormattingConversionService())
        .setMessageConverters(messageConverter)
        .setValidator(validator)
        .build();
  }
}