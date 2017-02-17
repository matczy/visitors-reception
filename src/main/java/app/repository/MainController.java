package app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {


    @Autowired
    private IndexedEntityRepository indexedEntityRepository;


    @RequestMapping(value = "/indexEntity", method = RequestMethod.GET)
    public void indexEntity() throws Exception {
        indexedEntityRepository.index();
    }

}

