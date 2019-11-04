package com.thecrunchycorner.mrpeacock.admin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdminConsoleControllerTest.class, BinEditControllerTest.class, AdminOrgEditControllerTest.class, 
    UserEditControllerTest.class })

public class AllIntTests
{

}
