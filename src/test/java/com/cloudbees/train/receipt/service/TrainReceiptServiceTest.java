package com.cloudbees.train.receipt.service;

import com.cloudbees.train.receipt.dto.SeatDTO;
import com.cloudbees.train.receipt.exceptions.UserNotFoundException;
import com.cloudbees.train.receipt.model.TrainReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cloudbees.train.receipt.util.TrainReceiptTestUtil.getTraineeReceipt;

public class TrainReceiptServiceTest {

    @InjectMocks
    private TrainReceiptService trainReceiptService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void given_validTraineeReceipt_when_save_then_shouldBeSaved(){
       TrainReceipt toBeSavedReceipt = getTraineeReceipt();
       trainReceiptService.save(toBeSavedReceipt);
       Assertions.assertNotNull(toBeSavedReceipt.getId());
    }

    @Test
    void given_validUserId_when_findById_then_shouldReturnTrainReceipt(){
        TrainReceipt savedReceipt = trainReceiptService.save(getTraineeReceipt());
        int id = savedReceipt.getId();
        Optional<TrainReceipt> receipt = trainReceiptService.findById(id);
        Assertions.assertNotEquals(Optional.empty(),receipt);
    }

    @Test
    void given_invalidUserId_when_findById_then_shouldReturnEmptyOptional(){
        trainReceiptService.save(getTraineeReceipt());
        int id = 0;
        Optional<TrainReceipt> receipt = trainReceiptService.findById(id);
        Assertions.assertEquals(Optional.empty(), receipt);
    }

    @Test
    void given_invalidUserId_when_remove_then_shouldNotRemove(){
        trainReceiptService.save(getTraineeReceipt());
        int beforeSize = trainReceiptService.findAll().size();
        trainReceiptService.remove(0);
        int afterSize = trainReceiptService.findAll().size();
        Assertions.assertEquals(beforeSize, afterSize);
    }

    @Test
    void given_validUserId_when_remove_then_shouldRemove(){
        TrainReceipt receipt = getTraineeReceipt();
        trainReceiptService.save(receipt);
        int beforeSize = trainReceiptService.findAll().size();
        trainReceiptService.remove(receipt.getId());
        int afterSize = trainReceiptService.findAll().size();
        Assertions.assertNotEquals(beforeSize, afterSize);
    }

    @Test
    void given_invalidUserId_when_updateSeatNumber_then_shouldThrowException(){
        Assertions.assertThrows(UserNotFoundException.class, () -> trainReceiptService.updateSeatNumber(0, new SeatDTO(1, "A")));
    }

    @Test
    void given_validUserId_when_updateSeatNumber_then_shouldUpdateSeatNumber() throws UserNotFoundException {
        TrainReceipt receipt = getTraineeReceipt();
        trainReceiptService.save(receipt);
        trainReceiptService.updateSeatNumber(receipt.getId(), new SeatDTO(2, "A"));
        String afterSection = receipt.getSection();
        int afterSeatNumber = receipt.getSeatNumber();
        Assertions.assertEquals(2, afterSeatNumber);
        Assertions.assertEquals("A", afterSection);
    }
}
