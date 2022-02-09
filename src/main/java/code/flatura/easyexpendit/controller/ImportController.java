package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.dto.ConsumableDto;
import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.service.CategoryService;
import code.flatura.easyexpendit.service.ConsumableService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import static code.flatura.easyexpendit.config.SecurityUtil.getLoggedUserId;
import static org.slf4j.LoggerFactory.getLogger;

@Controller("/import")
public class ImportController {
    private static final Logger LOG = getLogger(ConsumableService.class);

    private final ConsumableService consumableService;
    private final CategoryService categoryService;

    @Autowired
    public ImportController(ConsumableService consumableService, CategoryService categoryService) {
        this.consumableService = consumableService;
        this.categoryService = categoryService;
    }

    @GetMapping("/import/consumables")
    public ModelAndView importFromCsvForm(Map<String, Object> model) {
        UUID loggedUser = getLoggedUserId();
        LOG.info("User {} wants to upload CSV file for bulk add new consumables", loggedUser);
        return new ModelAndView("csvimport_consumables_form", model);
    }

    @PostMapping("/import/consumables")
    public ModelAndView importFromCsvSubmit(@RequestParam("file") MultipartFile file, Map<String, Object> model) {
        UUID loggedUser = getLoggedUserId();
        LOG.info("User {} uploads CSV file for bulk add new consumables", loggedUser);
        // Validating
        if (file.isEmpty()) {
            LOG.warn("User {} uploads empty CSV file", loggedUser);
            model.put("message", "Please select a CSV file to upload.");
            model.put("status", false);
            return new ModelAndView("redirect:/consumables", model);
        } else {
            // Parsing
            List<ConsumableDto> consumableDtos = readAndParseFile(file);
            LOG.info("Found {} new consumables to import. Showing results", consumableDtos.size());

            //TODO: Импорт тупой. Добавить подтверждение импорта, убрав моментальный импорт в рабочую базу.
            //TODO: Импорт тупой. Добавить редактирования категории.
            /*
            model.put("consumables_list", consumableDtos);
            List<Category> categories = categoryService.getAll();
            categories.sort(Comparator.comparing(Category::getName));
            model.put("categories_list", categories);

            model.put("status", true);
            */
            // Saving
            for (ConsumableDto c : consumableDtos) {
                consumableService.create(c);
                LOG.info("User {} saves new consumable {}, contract = {}, comment = {}, {} pieces", loggedUser, c.getName(), c.getContract(), c.getComment(), c.getCount());
            }
        }

        //return new ModelAndView("csvimport_consumables_confirm", model);
        return new ModelAndView("redirect:/consumables");
    }

    @PostMapping("/import/consumables/confirm")
    public ModelAndView importConsumablesConfirm(Map<String, Object> model,
                                                 @ModelAttribute(value = "consumables_list") List<ConsumableDto> consumableDtos) {
        //List<ConsumableDto> consumableDtos = (List<ConsumableDto>) model.getOrDefault("consumables_list", new ArrayList<ConsumableDto>());
        UUID loggedUser = getLoggedUserId();
        //Saving
        //List<Consumable> result = consumableService.createMultiple(consumableDtos);
        for (ConsumableDto c : consumableDtos) {
            LOG.info("User {} saves new consumable {}, {} pieces", loggedUser, c.getName(), c.getCount());
        }
        return new ModelAndView("redirect:/consumables");
    }


    private List<ConsumableDto> readAndParseFile(MultipartFile file) {
        List<ConsumableDto> result = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            //result = getDtoFromFileUsingCsvToBean(reader);
            result = getDtoFromFileUsingCsvToList(reader);
            LOG.info("Parsed {} DTOs", result.size());
        } catch (Exception ex) {
            //model.put("message", "An error occurred while processing the CSV file.");
            //model.put("status", false);
        }
        return result;
    }

    private List<ConsumableDto> getDtoFromFileUsingCsvToList(Reader reader) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(false)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        String[] line;
        List<ConsumableDto> list = new ArrayList<>();
        try {
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 7) {
                    list.add(new ConsumableDto(
                            line[0],
                            line[1],
                            Integer.parseInt(line[2]),
                            line[3],
                            Integer.parseInt(line[4]),
                            line[5],
                            Integer.parseInt(line[6]),
                            line[7]
                    ));
                    LOG.info("Successfully parsed new consumable: {}, {} pieces", line[0], line[6]);
                }
            }
            csvReader.close();
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<ConsumableDto> getDtoFromFileUsingCsvToBean(Reader reader) {
        CsvToBean<ConsumableDto> csvToBean = new CsvToBeanBuilder(reader)
                .withType(ConsumableDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(',')
                .build();
        return csvToBean.parse();
    }
}