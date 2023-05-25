SELECT
	*
FROM
	travel_participants_tb tpt
    left outer join schedule_tb st on(st.travel_id = tpt.travel_id)
	left outer join travel_routes_tb trt on(trt.schedule_id = st.schedule_id)
    left outer join location_tb lt on(lt.location_id = trt.location_id)
    left outer join region_tb rt on(lt.address like concat('%', rt.region_name, '%'))
    left outer join region_img_tb rit on (rit.region_id = rt.region_id)
where
	tpt.user_id = 2;
    
        SELECT
            tt.travel_id,
            tt.travel_name,

            tpt.participant_id,
            tpt.user_id,

            st.schedule_id,
            st.schedule_date,

            trt.route_id,
            trt.schedule_id,
            trt.location_id,

            lt.location_id,
            lt.address,
            lt.location_x,
            lt.location_y,
            
            rt.region_id,
		    rt.region_name,
		    rt.region_eng_name,
		    rt.region_description,
		    
		    rit.region_img_id,
		    rit.origin_name,
		    rit.temp_name,
		    rit.img_size
            
        FROM
            travels_tb tt
            LEFT OUTER JOIN travel_participants_tb tpt ON(tpt.travel_id = tt.travel_id)
            LEFT OUTER JOIN schedule_tb st ON(st.travel_id = tt.travel_id)
            LEFT OUTER JOIN travel_routes_tb trt ON(trt.schedule_id = st.schedule_id)
            LEFT OUTER JOIN location_tb lt ON(lt.location_id = trt.location_id)
            left outer join region_tb rt on(lt.address like concat('%', rt.region_name, '%'))
   			left outer join region_img_tb rit on (rit.region_id = rt.region_id)
        WHERE
            tpt.user_id = 2
        ORDER By
        	 tt.travel_id ASC, st.schedule_date ASC, lt.location_id ASC;