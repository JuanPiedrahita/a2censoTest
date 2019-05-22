#Declaring objects with css and xpath locators
@objects
    header              xpath   //*[@id="root"]/div/div/div[1]/div[1]/div/nav
    responsiveHeader    xpath   /html/body/div[1]/div/div/div[1]/div[2]/div/div/nav
    menuLinkConocenos   xpath   /html/body/div[1]/div/div/div[1]/div[1]/div/nav/div[2]/div/div[1]/a
    menuLinkFinanciate  xpath   /html/body/div[1]/div/div/div[1]/div[1]/div/nav/div[2]/div/div[2]/a
    menuLinkInvierte    xpath   /html/body/div[1]/div/div/div[1]/div[1]/div/nav/div[2]/div/div[3]/a
    menuLinkVitrina    xpath   /html/body/div[1]/div/div/div[1]/div[1]/div/nav/div[2]/div/div[4]/a

@groups
    menuLinks   menuLinkConocenos, menuLinkFinanciate, menuLinkInvierte, menuLinkVitrina

#Landing Page Tag
= Landing Page =
    @on desktop
        header:
            visible
        responsiveHeader:
            absent
        &menuLinks:
            visible
    @on tablet, mobile
        header:
            absent
        responsiveHeader:
            visible
        &menuLinks:
            absent