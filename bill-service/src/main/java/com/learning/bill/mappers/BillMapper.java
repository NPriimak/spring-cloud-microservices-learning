package com.learning.bill.mappers;

import com.learning.bill.model.dto.BillRequestDto;
import com.learning.bill.model.dto.BillResponseDto;
import com.learning.bill.model.dto.UpdateBillDto;
import com.learning.bill.model.entity.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BillMapper {
    public abstract Bill toEntity(BillResponseDto billDto);
    public abstract Bill toEntity(UpdateBillDto billDto);
    public abstract Bill toEntity(BillRequestDto billDto);

    public abstract BillResponseDto toDto(Bill bill);
}
