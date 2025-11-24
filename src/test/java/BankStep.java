import com.example.firstproject.controller.BankController;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.BankServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


@RequiredArgsConstructor
public class BankStep {

    private CompteDto compteDto;
    private BankController bankController;
    private final BankServiceImpl bankService;
    private final CompteRepository compteRepository;
    private String nomEnEntree;

    @BeforeEach
    void initialisation() {
        bankController = new BankController(bankService);
    }

    @Given("toutes les informations pour creer un compte {string} {string} {string} {string}")
    public void creerCompte(String firstName, String lastName, String numeroCompte, String solde) {
        compteDto.setNumeroCompte(numeroCompte);
        compteDto.setFirstName(firstName);
        compteDto.setLastName(lastName);
        compteDto.setSolde(Double.parseDouble(solde));
    }

    @When("si le compte {string} n'existe pas")
    public void verifierCompteExistePas(String numeroCompte) {
        Assertions.assertTrue(compteRepository.findById(numeroCompte).isEmpty());
    }

    @When("si tu me demande la 1iere lettre de {string}")
    public void when(String nom) {
        System.out.println("Trouver la 1iere lettre de " + nom);
    }

    @Then("je te retourne la lettre {string}")
    public void resultatAttendu(String lettre) {
        Assertions.assertEquals(lettre.charAt(0), nomEnEntree.charAt(0));
    }
}
