package com.ssafy.info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicDto {
	int no;
	String do_city;
	String city_gun_borough;
	String hospital_name;
	String hospital_address;
	String operating_hours_weekday;
	String operating_hours_saturday;
	String operating_hours_sunday;
	String operating_hours_holiday;
	String representative_phone_number;
	String local_public_health_center;
	String local_public_health_center_phone_number;
	String accommodation_for_disabled_person;
}
