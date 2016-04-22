package comand;

public class ExitException extends RuntimeException { //сделал из Throuble RuntimeException что бы не нужно было его чекать,
// это такой маркер ексепшион (3л 1-26-40)
    // потому что не хочу его нигде ловить, словлю - ок, если не словлю - выскочит на самый верх
}
