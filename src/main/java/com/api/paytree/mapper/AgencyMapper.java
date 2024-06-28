package com.api.paytree.mapper;

import com.api.paytree.dto.AgencyDto.*;
import com.api.paytree.dto.SearchDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyMapper {
    List<Distributor> getDistributorListFilter();

    Virtual getVirtualWalletFilter();

    List<Agency> getAgencyList(Search search);

    AgencyDetail getAgencyDetail(String agencyId);

    int insertAgency(AgencyDetail agencyDetail);

    List<Agency> getBelongAgencyList(String code);

    int updateAgency(AgencyDetail agencyDetail);

    List<AgencyDetail> getAgencyWalletList(Search search);

    int getAdminBalance();

    int updateAdminBalance(int amount);

    int updateAgencyBalance(String agencyId, int amount);
}