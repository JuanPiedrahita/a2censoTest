#Declaring objects with css and xpath locators
@objects
  header               xpath   //*[@id="root"]/div/div/div[1]/div[1]/div/nav

#Home Page Tag
= Home Page =
   @on desktop
       header:
           inside screen 0px top
           inside screen 0px left
           inside screen 0px right
   @on tablet
       header:
           inside screen 0px top
           inside screen 0px left
           inside screen 0px right
   @on mobile
       header:
           inside screen 0px top
           inside screen 0px left
           inside screen 0px right