package vn.com.rabbit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.com.rabbit.base.entity.BaseEntity;
import vn.com.rabbit.base.models.annotation.ReportTableName;

@Data
@Entity
@Table
@ReportTableName(value = "SettingGroup", name = "SettingGroup")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SettingGroup extends BaseEntity {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "order_no")
	private String orderNo;
	
	@ManyToOne
    @JoinColumn(name = "setting_group_id")
    private Setting settings;
}
