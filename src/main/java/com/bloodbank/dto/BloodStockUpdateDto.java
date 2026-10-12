package com.bloodbank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BloodStockUpdateDto {
    @NotBlank(message = "Blood group is required")
    private String bloodGroup;

    @NotNull(message = "Units is required")
    @Min(value = 0, message = "Units cannot be negative")
    private Integer units;

    private Integer minimumThreshold;

    public BloodStockUpdateDto() {}

    public BloodStockUpdateDto(String bloodGroup, Integer units, Integer minimumThreshold) {
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.minimumThreshold = minimumThreshold;
    }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public Integer getUnits() { return units; }
    public void setUnits(Integer units) { this.units = units; }

    public Integer getMinimumThreshold() { return minimumThreshold; }
    public void setMinimumThreshold(Integer minimumThreshold) { this.minimumThreshold = minimumThreshold; }
}
