package uz.pdp.program_47.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.program_47.entity.Card;
import uz.pdp.program_47.entity.InCome;
import uz.pdp.program_47.entity.OutCome;
import uz.pdp.program_47.payload.OutComeDto;
import uz.pdp.program_47.payload.Result;
import uz.pdp.program_47.repository.CardRepository;
import uz.pdp.program_47.repository.InComeRepository;
import uz.pdp.program_47.repository.OutComeRepository;
import uz.pdp.program_47.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TransferService {
@Autowired
    InComeRepository inComeRepository;
@Autowired
    OutComeRepository outComeRepository;
@Autowired
    CardRepository cardRepository;
@Autowired
    JwtProvider jwtProvider;



public Result add(HttpServletRequest request, OutComeDto outComeDto){
    boolean existsByIdAndActive  = cardRepository.existsByIdAndActive(outComeDto.getFromCardId(), true);
if (!existsByIdAndActive){
    return new Result(outComeDto.getFromCardId()+" : such card id not exist!", false);
}
boolean existsByIdAndActive2 = cardRepository.existsByIdAndActive(outComeDto.getToCardId(), true);
if (!existsByIdAndActive2){
    return new Result(outComeDto.getToCardId()+" : such card id not exist!", false);
}
    Optional<Card> optionalCard = cardRepository.findById(outComeDto.getFromCardId());
Card card1 = optionalCard.get();
optionalCard = cardRepository.findById(outComeDto.getToCardId());
Card card2= optionalCard.get();

String token = request.getHeader("Authorization");
token = token.substring(7);
String username = jwtProvider.getUsernameFromToken(token);
if (!username.equals(card1.getUsername())){
    return new Result("This card belongs to someone else!", false);
}
if (card1.getBalance() < (outComeDto.getAmount() + outComeDto.getAmount()*0.01)){
    return new Result("Sizning hisobingizda mablag' yetarli emas!", false);
}
OutCome outCome = new OutCome();
outCome.setFromCard(card1);
outCome.setToCard(card2);
outCome.setAmount(outComeDto.getAmount());
outCome.setCommissionAmount(outComeDto.getAmount()*0.01);
outComeRepository.save(outCome);

    InCome inCome = new InCome();
    inCome.setFromCard(card1);
    inCome.setToCard(card2);
    inCome.setAmount(outComeDto.getAmount());
    inComeRepository.save(inCome);

card1.setBalance(card1.getBalance() - (outComeDto.getAmount() + outComeDto.getAmount()*0.01));
cardRepository.save(card1);

card2.setBalance(card2.getBalance() + outComeDto.getAmount());
cardRepository.save(card2);

return new Result("Money successfully transfer!", true);
}


public Result getOutCome(int page){
    boolean existsOutCome = outComeRepository.existsOutCome();
    if (!existsOutCome){
        return new Result("Transfers not exist from card to card!", false);
    }
    Pageable pageable = PageRequest.of(page, 20);
    Page<OutCome> page1 = outComeRepository.findAll(pageable);
    return new Result(page1, true);
}

public Result getOutComeByFromCardId(Integer id, int page){
    boolean existsByFromCardId = outComeRepository.existsByFromCardId(id);
    if (!existsByFromCardId){
        return new Result("From this card did not do any transfer!", false);
    }
    Pageable pageable = PageRequest.of(page, 20);
    Page<OutCome> page1 = outComeRepository.getByFromCardId(id, pageable);
    return new Result(page1, true);
}

public Result getOutComeById(Integer id){
    boolean existsById = outComeRepository.existsById(id);
    if (!existsById){
        return new Result("Such outCome id not exists!",false);
    }
    Optional<OutCome> optionalOutCome = outComeRepository.findById(id);
    return new Result(optionalOutCome.get(), true);
}

public Result getInCome(int page){
    boolean existsInCome = inComeRepository.existsInCome();
    if (!existsInCome){
        return new Result("Transfers not exist from other cards to this card!", false);
    }
    Pageable pageable = PageRequest.of(page, 20);
    Page<InCome> page1 = inComeRepository.findAll(pageable);
    return new Result(page1, true);
}

public Result getInComeByToCardId(Integer id, int page){
    boolean existsByToCardId = inComeRepository.existsByToCardId(id);
    if (!existsByToCardId){
        return new Result("To this card did not do any transfer!", false);
    }
    Pageable pageable = PageRequest.of(page, 20);
    Page<InCome> page1 = inComeRepository.getByToCardId(id, pageable);
    return new Result(page1, true);
}

public Result getInComeById(Integer id){
    boolean existsById = inComeRepository.existsById(id);
    if (!existsById){
        return new Result("Such income id not exist!", false);
    }
    Optional<InCome> optionalInCome = inComeRepository.findById(id);
    return new Result(optionalInCome.get(), true);
}

// Pul o'tkazilib bo'lganidan so'ng, o'tkazilganlik haqidagi malumotni o'zgartirib bo'lmasin
    // chunki malumotni o'zgartirgandan foyda yo'q, pul baribir o'tib bo'lgan bo'ladi!

    public Result deleteOutComeById(Integer id){
    boolean existsOutComeById = outComeRepository.existsById(id);
    if (!existsOutComeById){
        return new Result("Such outCome id not exist!", false);
    }
    outComeRepository.deleteById(id);
    return new Result("Given outCome successfully deleted.", true);
    }

    public Result deleteInComeById(Integer id){
    boolean existsInComeById = inComeRepository.existsById(id);
    if (!existsInComeById){
        return new Result("Such income id not exist!", false);
    }
    inComeRepository.deleteById(id);
    return new Result("Given income successfully deleted.", true);
    }

}
