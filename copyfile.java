public class copyfile {
    try{
        if(cmdParts.length<3)
            throw new ExInsufficientArguments();
        
    } catch(ExEmployeeNotFound e){
        System.out.println(e.getMessage());
    } catch(ExTeamNotFound e){
        System.out.println(e.getMessage());
    } catch(ExInsufficientArguments e){
        System.out.println(e.getMessage());
    }
}
