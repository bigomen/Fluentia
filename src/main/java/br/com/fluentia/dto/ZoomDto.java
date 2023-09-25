package br.com.fluentia.dto;

import java.util.HashMap;
import java.util.Map;

public class ZoomDto {

	private String topic;
	private String startTime;
	private String duration;
	private String scheduleFor;
	private Map<String,Object> mapZoomDto;
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getScheduleFor() {
		return scheduleFor;
	}
	public void setScheduleFor(String scheduleFor) {
		this.scheduleFor = scheduleFor;
	}
	public Map<String, Object> getMapZoomDto() {
		mapZoomDto = new HashMap<>();
		var mapSettingDto = new HashMap<>();
		mapSettingDto.put("host_video", "true");
		mapSettingDto.put("participant_video", "true");
		mapSettingDto.put("join_before_host", "false");
		mapSettingDto.put("mute_upon_entry", "true");
		mapSettingDto.put("watermark", "false");
		mapSettingDto.put("approval_type", "0");
		mapZoomDto.put("settings", mapSettingDto);
		mapZoomDto.put("type", "2");
		mapZoomDto.put("timezone", "America/Sao_Paulo");
		mapZoomDto.put("password", "Fluentia");
		mapZoomDto.put("topic", this.topic);
		mapZoomDto.put("start_time", this.startTime);
		mapZoomDto.put("duration", this.duration);
		mapZoomDto.put("schedule_for", this.scheduleFor);
		return mapZoomDto;
	}
	
}
//{
//	  "topic": "AULA TESTE DE API",
//	  "type": "2",
//	  "start_time": "2021-12-25T08:00:00",
//	  "duration": "70",
//	  "schedule_for": "FZSeAmDjTNKWQ4lWo7m0Rg",
//	  "timezone": "America/Sao_Paulo",
//	  "password": "Fluentia21",
//	  "settings": {
//	    "host_video": "true",
//	    "participant_video": "true",
//	    "join_before_host": "false",
//	    "mute_upon_entry": "true",
//	    "watermark": "false",
//	    "approval_type": "0"
//	  }
//	}