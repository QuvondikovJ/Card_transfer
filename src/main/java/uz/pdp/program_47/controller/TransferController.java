package uz.pdp.program_47.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.program_47.payload.OutComeDto;
import uz.pdp.program_47.payload.Result;
import uz.pdp.program_47.service.TransferService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
@Autowired
TransferService transferService;


@PostMapping
public ResponseEntity<Result> add(@RequestBody OutComeDto outComeDto, HttpServletRequest request){
    Result result = transferService.add(request, outComeDto);
    if (result.isActive()){
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    return ResponseEntity.status(409).body(result);
}

@GetMapping("/outcome")
    public ResponseEntity<Result> getOutCome(@RequestParam int page){
    Result result = transferService.getOutCome(page);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(204).body(result);
}

@GetMapping("/outcome/fromCard/{id}")
    public ResponseEntity<Result> getOutComeByFromCardId(@PathVariable Integer id,@RequestParam int page){
    Result result = transferService.getOutComeByFromCardId(id, page);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(204).body(result);
}

@GetMapping("/outcome/{id}")
public ResponseEntity<Result> getOutComeById(@PathVariable Integer id){
    Result result = transferService.getOutComeById(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}


@GetMapping("/income")
    public ResponseEntity<Result> getInCome(@RequestParam int page){
    Result result = transferService.getInCome(page);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(204).body(result);
}


@GetMapping("/income/toCard/{id}")
    public ResponseEntity<Result> getInComeByToCardId(@PathVariable Integer id, @RequestParam int page) {
    Result result = transferService.getInComeByToCardId(id, page);
if (result.isActive()){
    return ResponseEntity.ok(result);
}
return ResponseEntity.status(204).body(result);
}

@GetMapping("/income/{id}")
    public  ResponseEntity<Result> getInComeById(@PathVariable Integer id){
    Result result =transferService.getInComeById(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}

@DeleteMapping("/outcome/{id}")
    public ResponseEntity<Result> deleteOutComeById(@PathVariable Integer id){
    Result result = transferService.deleteOutComeById(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}

@DeleteMapping("/income/{id}")
    public ResponseEntity<Result> deleteInComeById(@PathVariable Integer id){
    Result result = transferService.deleteInComeById(id);
    if (result.isActive()){
        return ResponseEntity.ok(result);
    }
    return ResponseEntity.status(409).body(result);
}

}
