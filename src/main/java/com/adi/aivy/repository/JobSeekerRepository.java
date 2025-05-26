package com.adi.aivy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adi.aivy.entity.JobSeeker;

public interface JobSeekerRepository extends MongoRepository<JobSeeker, String> {

	JobSeeker findByQualificationsAndExtractedCv(String qualifications, String extractedCv);

}