package com.bloodbank.service;

import com.bloodbank.dto.BloodStockUpdateDto;
import com.bloodbank.entity.BloodStock;
import com.bloodbank.exception.InsufficientStockException;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.BloodStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BloodStockService {

    private final BloodStockRepository bloodStockRepository;

    @Autowired
    public BloodStockService(BloodStockRepository bloodStockRepository) {
        this.bloodStockRepository = bloodStockRepository;
    }

    public List<BloodStock> getAllStock() {
        return bloodStockRepository.findAll();
    }

    public BloodStock getStockByBloodGroup(String bloodGroup) {
        return bloodStockRepository.findByBloodGroup(bloodGroup.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Blood group " + bloodGroup + " not found in inventory"));
    }

    @Transactional
    public BloodStock updateStock(BloodStockUpdateDto dto) {
        String bg = dto.getBloodGroup().trim().toUpperCase();
        BloodStock stock = bloodStockRepository.findByBloodGroup(bg)
                .orElseGet(() -> new BloodStock(null, bg, 0, 10));

        stock.setUnitsAvailable(dto.getUnits());
        if (dto.getMinimumThreshold() != null) {
            stock.setMinimumThreshold(dto.getMinimumThreshold());
        }
        return bloodStockRepository.save(stock);
    }

    @Transactional
    public void incrementStock(String bloodGroup, int units) {
        String bg = bloodGroup.trim().toUpperCase();
        BloodStock stock = bloodStockRepository.findByBloodGroup(bg)
                .orElseGet(() -> new BloodStock(null, bg, 0, 10));

        stock.setUnitsAvailable(stock.getUnitsAvailable() + units);
        bloodStockRepository.save(stock);
    }

    @Transactional
    public void decrementStock(String bloodGroup, int units) {
        String bg = bloodGroup.trim().toUpperCase();
        BloodStock stock = bloodStockRepository.findByBloodGroup(bg)
                .orElseThrow(() -> new ResourceNotFoundException("Blood group " + bg + " is not available in inventory"));

        if (stock.getUnitsAvailable() < units) {
            throw new InsufficientStockException("Insufficient blood units available for " + bg +
                    ". Available: " + stock.getUnitsAvailable() + ", Requested: " + units);
        }

        stock.setUnitsAvailable(stock.getUnitsAvailable() - units);
        bloodStockRepository.save(stock);
    }

    public boolean isAvailable(String bloodGroup, int requestedUnits) {
        return bloodStockRepository.findByBloodGroup(bloodGroup.trim().toUpperCase())
                .map(stock -> stock.getUnitsAvailable() >= requestedUnits)
                .orElse(false);
    }

    public Long getTotalAvailableUnits() {
        Long total = bloodStockRepository.getTotalAvailableUnits();
        return total != null ? total : 0L;
    }
}
