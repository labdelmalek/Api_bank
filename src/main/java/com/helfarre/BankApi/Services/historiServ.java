package com.helfarre.BankApi.Services;

import java.util.List;

import com.helfarre.BankApi.Entities.historiqueadmin;

public interface historiServ {
	List<historiqueadmin>getallbyagence(Long idagence);

}
