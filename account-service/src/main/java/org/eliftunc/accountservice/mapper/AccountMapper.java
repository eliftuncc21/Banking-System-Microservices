package org.eliftunc.accountservice.mapper;

import org.eliftunc.accountservice.dto.AccountRequestDto;
import org.eliftunc.accountservice.dto.AccountResponseDto;
import org.eliftunc.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountRequestDto accountRequestDto);

    AccountResponseDto toAccountResponseDto(Account account);

    @Mapping(target="accountId", ignore = true)
    void updateAccount(AccountRequestDto accountRequestDto, @MappingTarget Account account);
}
