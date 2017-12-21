package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job singleJob = jobData.findById(id);

        model.addAttribute("singleJob", singleJob);



        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm,  Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {

            return "new-job";
        }



        String name = jobForm.getName();
        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int skillId = jobForm.getSkillId();
        int positionTypeId = jobForm.getPositionTypeId();



        Employer employer = jobData.getEmployers().findById(employerId);
        jobData.getEmployers().add(employer);

        Location location = jobData.getLocations().findById(locationId);
        jobData.getLocations().add(location);

        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(skillId);
        jobData.getCoreCompetencies().add(coreCompetency);

        PositionType positionType = jobData.getPositionTypes().findById(positionTypeId);
        jobData.getPositionTypes().add(positionType);

        Job singleJob = new Job (name, employer, location, positionType, coreCompetency);

        jobData.add(singleJob);
        int id = singleJob.getId();

        return "redirect:/job?id=" + id;

    }
}
