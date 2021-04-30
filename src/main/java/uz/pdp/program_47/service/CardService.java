package uz.pdp.program_47.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.program_47.entity.Card;
import uz.pdp.program_47.payload.Result;
import uz.pdp.program_47.repository.CardRepository;
import uz.pdp.program_47.security.JwtProvider;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;
    @Autowired
    CardRepository cardRepository;


    public Result add(Card card){
        UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
        boolean existsByNumberAndActive = cardRepository.existsByNumberAndActive(card.getNumber(), true);
    if (existsByNumberAndActive){
        return new Result("Such card number belongs to someone else!", false);
    }

    cardRepository.save(card);
    return new Result("New card successfully saved.", true);
    }


    public Result get(int page){
        boolean existsByActive = cardRepository.existsByActive(true);
        if (!existsByActive){
            return new Result("Cards not exist yet!", false);
        }
        Pageable pageable = PageRequest.of(page, 20);
        Page<Card> page1 = cardRepository.getByActive(true, pageable);
        return new Result(page1, true);
    }

    public Result getById(Integer id){
        boolean existsByIdAndActive = cardRepository.existsByIdAndActive(id, true);
        if (!existsByIdAndActive){
            return new Result("Such card id not exist!", false);
        }
        Optional<Card> optionalCard = cardRepository.findById(id);
        return new Result(optionalCard.get(), true);
    }


    public Result edit(Integer id, Card card){
        boolean existsByIdAndActive = cardRepository.existsByIdAndActive(id, true);
        if (!existsByIdAndActive){
            return new Result("Such card id not exist!", false);
        }
        Optional<Card> optionalCard = cardRepository.findById(id);
        Card card1 = optionalCard.get();

        UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
        boolean existsByNumberAndActive = cardRepository.existsByNumberAndActiveAndIdNot(card.getNumber(), true, id);
        if (existsByNumberAndActive){
            return new Result("This card number belongs to someone else!", false);
        }
        card1.setUsername(card.getUsername());
card1.setNumber(card.getNumber());
card1.setBalance(card.getBalance());
cardRepository.save(card1);
return new Result("Given card successfully edited.", true);
    }


    public Result delete(Integer id){
        boolean existsByIdAndActive = cardRepository.existsByIdAndActive(id, true);
        if (!existsByIdAndActive){
            return new Result("Such card id not exist!", false);
        }
        Optional<Card> optionalCard = cardRepository.findById(id);
        Card card = optionalCard.get();
        card.setActive(false);
        cardRepository.save(card);
        return new Result("Given card successfully deleted.", true);
    }


}
