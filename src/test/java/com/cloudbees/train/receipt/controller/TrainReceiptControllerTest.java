package com.cloudbees.train.receipt.controller;

import com.cloudbees.train.receipt.dto.SeatDTO;
import com.cloudbees.train.receipt.exceptions.UserNotFoundException;
import com.cloudbees.train.receipt.model.TrainReceipt;
import com.cloudbees.train.receipt.service.TrainReceiptService;
import com.cloudbees.train.receipt.util.TrainReceiptTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(TrainReceiptController.class)
public class TrainReceiptControllerTest {

    private static final String BASE_URI = "/train-receipt";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainReceiptService trainReceiptService;


    @Test
    void given_ValidUserReceiptId_when_getReceiptByUserId_thenReturnSuccess() throws Exception {
        TrainReceipt trainReceipt = TrainReceiptTestUtil.getTraineeReceipt();
        when(trainReceiptService.findById(1)).thenReturn(Optional.of(trainReceipt));
        this.mockMvc.perform(get(BASE_URI + "/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void given_inValidUserReceiptId_when_getReceiptByUserId_thenReturnNotFound() throws Exception {
        TrainReceipt trainReceipt = TrainReceiptTestUtil.getTraineeReceipt();
        when(trainReceiptService.findById(1)).thenReturn(Optional.of(trainReceipt));
        this.mockMvc.perform(get(BASE_URI + "/10")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void given_ValidUserReceiptId_when_updateSeat_thenReturnSuccess() throws Exception {
        SeatDTO seat = new SeatDTO(1, "Z");
        String content = objectMapper.writeValueAsString(seat);
        Mockito.doNothing().when(trainReceiptService).updateSeatNumber(Mockito.anyInt(), Mockito.any());
        this.mockMvc.perform(patch(BASE_URI + "/1/seat").content(content).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void given_inValidUserReceiptId_when_updateSeat_thenReturnSuccess() throws Exception {
        SeatDTO seat = new SeatDTO(1, "Z");
        String content = objectMapper.writeValueAsString(seat);
        Mockito.doThrow(new UserNotFoundException("Passenger not found")).when(trainReceiptService).updateSeatNumber(Mockito.anyInt(), Mockito.any());
        this.mockMvc.perform(patch(BASE_URI + "/1/seat").content(content).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isUnprocessableEntity());
    }
}
