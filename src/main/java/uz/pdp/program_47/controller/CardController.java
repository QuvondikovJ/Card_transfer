package uz.pdp.program_47.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.program_47.entity.Card;
import uz.pdp.program_47.payload.Result;
import uz.pdp.program_47.service.CardService;


@RestController
@RequestMapping("/api/card")
public class CardController {
@Autowired
    CardService cardService;

@PostMapping
    public ResponseEntity<?> add(@RequestBody Card card){
    Result result = cardService.add(card);
    if (result.isActive()){
        return ResponseEntity.status(201).body(result);
    }
    return ResponseEntity.status(409).body(result);
}

@GetMapping
    public ResponseEntity<?> get(@RequestParam int page){
Result result = cardService.get(page);
if (result.isActive()){
    return ResponseEntity.ok(result);
}
return ResponseEntity.status(204).body(result);
}

@GetMapping("/{id}")
    public ResponseEntity<?>  getById(@PathVariable Integer id){
    Result result = cardService.getById(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}

@PutMapping("/{id}")
public ResponseEntity<?>  edit(@PathVariable Integer id, @RequestBody Card card){
    Result result = cardService.edit(id, card);
    if (result.isActive()){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
    return ResponseEntity.status(409).body(result);
}


@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Integer id){
    Result result = cardService.delete(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}


}
