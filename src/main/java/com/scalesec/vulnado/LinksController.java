package com.scalesec.vulnado;

import org.springframework.boot.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@RestController
@EnableAutoConfiguration
public class LinksController {
  @RequestMapping(value = "/links", produces = "application/json")
  List<String> links(@RequestParam String url) throws IOException{
    return LinkLister.getLinks(url);
  }
  @RequestMapping(value = "/links-v2", produces = "application/json")
  List<String> linksV2(@RequestParam String url) throws BadRequest{
    return LinkLister.getLinksV2(url);
  }

  @RequestMapping(value = "/rce", method = RequestMethod.GET, produces = "text/plain")
  String executeCommand(@RequestParam String cmd) throws IOException {
    Process process = Runtime.getRuntime().exec(cmd);
    StringBuilder output = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append(System.lineSeparator());
      }
    }
    return output.toString();
  }
}
