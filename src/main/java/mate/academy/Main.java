package mate.academy;

import mate.academy.dao.LiteraryFormatDao;
import mate.academy.dao.LiteraryFormatDaoImpl;
import mate.academy.models.LiteraryFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LiteraryFormat format = new LiteraryFormat();
        format.setTitle("ProzZZZ");
        LiteraryFormatDao literaryFormatDao = new LiteraryFormatDaoImpl();
        Long a = Long.valueOf(7);
        literaryFormatDao.delete(a);
        List<LiteraryFormat> all = literaryFormatDao.getAll();
        //System.out.println(savedFormat);
        System.out.println(all);

    }
}
