#Declaring objects with css and xpath locators
@objects
    modalContainer     xpath   /html/body/div[2]/div/div[1]/div/div
    image              xpath   /html/body/div[2]/div/div[1]/div/div/form/div[1]/img
    title              xpath   //*[@id="user-form"]/div[1]/p
    userNameField      xpath   //*[@id="user-form"]/div[2]/div[1]/div/input
    mailField          xpath   //*[@id="user-form"]/div[2]/div[2]/div/input
    indicativeField    xpath   //*[@id="user-form"]/div[3]/div[1]/div/input
    cellNumberField    xpath   //*[@id="user-form"]/div[3]/div[2]/div/input
    pqrsTypeSelect     xpath   //*[@id="tipoPqrs"]
    pqrsSubjectField   xpath   //*[@id="user-form"]/div[4]/div/div/input
    messageField       xpath   //*[@id="user-form"]/div[5]/div/textarea
    terms_desktop      xpath   //*[@id="user-form"]/div[6]/div/div
    terms_mobile       xpath   //*[@id="user-form"]/div[7]/div


@groups
    cellPhoneField  indicativeField, cellNumberField

@set
    formHorizontalSpace    5 to 15 px
    formVerticalSpace    <= 110 px
    responsiveVerticalSpace <= 110 px

#Landing Page Tag
= Landing Page =
    @on desktop, tablet
        image, title:
            visible
            inside modalContainer
        title:
            centered horizontally inside modalContainer
        userNameField:
            visible
            below title
        mailField:
            visible
            right-of userNameField ${formHorizontalSpace}
            aligned horizontally all userNameField
        &cellPhoneField:
            visible
        cellNumberField:
            visible
            aligned horizontally all indicativeField
        pqrsTypeSelect:
            right-of cellNumberField ${formHorizontalSpace}
            aligned horizontally bottom cellNumberField
            visible
        pqrsSubjectField:
            below cellNumberField ${formVerticalSpace}
            visible
        messageField:
            visible
            below pqrsSubjectField ${formVerticalSpace}
        terms_desktop:
            visible
            below  messageField ${formVerticalSpace}
        terms_mobile:
            absent

    @on mobile
        image, title:
            visible
            inside modalContainer
        title:
            centered horizontally inside modalContainer
        userNameField:
            visible
            below title ${responsiveVerticalSpace}
        mailField:
            visible
            below userNameField ${responsiveVerticalSpace}
        &cellPhoneField:
            visible
        indicativeField:
            below mailField ${responsiveVerticalSpace}
        cellNumberField:
            below indicativeField ${responsiveVerticalSpace}
        pqrsTypeSelect:
            below cellNumberField ${responsiveVerticalSpace}
            visible
        pqrsSubjectField:
            below cellNumberField ${responsiveVerticalSpace}
            visible
        messageField:
            visible
            below pqrsSubjectField ${responsiveVerticalSpace}
        terms_desktop:
            absent
        terms_mobile:
            visible
            below  messageField ${responsiveVerticalSpace}

