package com.cloudbees.train.receipt.service;

import com.cloudbees.train.receipt.dto.SeatAllocationDTO;
import com.cloudbees.train.receipt.dto.SeatDTO;
import com.cloudbees.train.receipt.exceptions.UserNotFoundException;
import com.cloudbees.train.receipt.model.TrainReceipt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TrainReceiptService {

    private final Map<Integer, TrainReceipt> trainReceiptMap = new HashMap<>();
    private Integer id = 1;

    public TrainReceipt save(TrainReceipt trainReceipt){
        trainReceipt.setId(id);
        trainReceiptMap.put(trainReceipt.getId(), trainReceipt);
        return trainReceiptMap.get(id++);
    }

    public Optional<TrainReceipt> findById(Integer id){
        return Optional.ofNullable(trainReceiptMap.get(id));
    }

    public List<TrainReceipt> findAll(){
        return new ArrayList<>(trainReceiptMap.values());
    }

    public List<SeatAllocationDTO> seatAllocationDetailsBySection(String section){
        List<TrainReceipt> receipts = findAll();
        return receipts.stream().filter(receipt -> receipt.getSection().equals(section)).map(
                receipt -> new SeatAllocationDTO(receipt.getPassenger(), receipt.getSeatNumber())
        ).toList();
    }

    public void remove(Integer id) throws UserNotFoundException {
        Optional<TrainReceipt> trainReceipt = findById(id);
        if(trainReceipt.isEmpty()){
            throw new UserNotFoundException("Passenger id does not exist");
        }
        trainReceiptMap.remove(id);
    }

    public void updateSeatNumber(Integer userReceiptId, SeatDTO seat) throws UserNotFoundException {
        Optional<TrainReceipt> trainReceipt = findById(userReceiptId);
        if(trainReceipt.isEmpty()){
            throw new UserNotFoundException("Passenger does not exist");
        }
        trainReceipt.get().setSeatNumber(seat.seatNumber());
        trainReceipt.get().setSection(seat.section());
        update(trainReceipt.get());
    }

    public TrainReceipt update(TrainReceipt receipt){
        return trainReceiptMap.put(receipt.getId(), receipt);
    }
}
