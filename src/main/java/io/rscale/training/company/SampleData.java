package io.rscale.training.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cloud")
public class SampleData implements CommandLineRunner {

    private final CompanyRepository repository;

    @Autowired
    public SampleData(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {	 
    	add(new Company("Resilient Scale", new String[]{"Resilient Scale, Inc", "Resilient Scale Inc", "RScale"}, "https://rscale.io", "resilientscale"));
    	add(new Company("Cloud Foundry", new String[]{"Cloud Foundry Foundation", "CFF"}, "https://cloudfoundry.org", "cloudfoundry"));
        repository.findAll().forEach(System.out::println);
    }
    	
    private void add(Company company) throws Exception {
    	if (repository.findByNameIgnoreCase(company.getName()).isEmpty()) {
    		repository.save(company);
    	}
    }
    
}