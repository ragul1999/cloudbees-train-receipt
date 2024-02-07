package com.cloudbees.train.receipt.controller;

import com.cloudbees.train.receipt.dto.SeatAllocationDTO;
import com.cloudbees.train.receipt.dto.SeatDTO;
import com.cloudbees.train.receipt.exceptions.UserNotFoundException;
import com.cloudbees.train.receipt.model.TrainReceipt;
import com.cloudbees.train.receipt.service.TrainReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("train-receipt")
public class TrainReceiptController {
    private final TrainReceiptService trainReceiptService;

    public TrainReceiptController(TrainReceiptService trainReceiptService){
        this.trainReceiptService = trainReceiptService;
    }

    @PostMapping
    public ResponseEntity<TrainReceipt> saveReceipt(@RequestBody TrainReceipt trainReceipt){
        TrainReceipt savedReceipt = trainReceiptService.save(trainReceipt);
        return ResponseEntity.ok(savedReceipt);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<TrainReceipt> getReceiptByUserId(@PathVariable Integer userId){
        Optional<TrainReceipt> receipt = trainReceiptService.findById(userId);
        return receipt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{section}/seat-allocations")
    public ResponseEntity<List<SeatAllocationDTO>> getReceiptByUserId(@PathVariable String section){
        List<SeatAllocationDTO> seatAllocations = trainReceiptService.seatAllocationDetailsBySection(section);
        return seatAllocations.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(seatAllocations);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removePassenger(@PathVariable Integer userId){
        Optional<TrainReceipt> trainReceipt = trainReceiptService.findById(userId);
        if(trainReceipt.isEmpty()){
            return ResponseEntity.unprocessableEntity().body("Passenger id does not exist");
        }
        trainReceiptService.remove(userId);
        return ResponseEntity.ok("Passenger removed successfully");
    }

    @PatchMapping("/{userId}/seat")
    public ResponseEntity<String> updateSeat(@PathVariable Integer userId, @RequestBody SeatDTO seat){
        try {
            trainReceiptService.updateSeatNumber(userId, seat);
        }catch (UserNotFoundException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok("Passenger seat details updated successfully");
    }
}
