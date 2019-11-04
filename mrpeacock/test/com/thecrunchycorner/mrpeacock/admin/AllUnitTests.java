package com.thecrunchycorner.mrpeacock.admin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdminLogonValidatorTest.class, BinValidatorTest.class, OrgValidatorTest.class,
    UserValidatorTest.class })

public class AllUnitTests
{

}
