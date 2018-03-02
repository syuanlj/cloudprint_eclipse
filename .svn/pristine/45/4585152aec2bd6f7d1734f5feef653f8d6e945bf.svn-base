<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.firmware.add")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $fileForm = $("#fileForm");
	var $dataForm = $("#dataForm");
	var $previewButton = $("#previewButton");
	var $saveButton = $("#saveButton");

	[@flash_message /]

	[#list firmwares as firmware]
	var $areaId${firmware_index} = $("#areaId${firmware_index}");
	$areaId${firmware_index}.lSelect({
		url: "${base}/admin/common/area.ehtml"
	});
	[/#list]

	$previewButton.click(function() {
		$fileForm.submit();
	});

	$saveButton.click(function() {
		$dataForm.submit();
	});

	$(".productSn").blur(function() {
		var $this = $(this);
		$.ajax({
			url: "check_sn.ehtml",
			type: "GET",
			data: {productSn: $this.val()},
			dataType: "json",
			success: function(data) {
				if(!data) {
					alert("${message("admin.validate.notExist")}");
					$this.val("");
				}
			}
		});
	});

	$fileForm.validate({
		rules: {
			"firmwareFile": {
				required: true,
				extension: "${setting.firmwareFileExtension}"
			}
		},
		submitHandler: function(form) {
			form.submit();
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.firmware.add")}
	</div>
	<form id="fileForm" action="preview.ehtml" method="post" enctype="multipart/form-data">
		<table class="input">
			<tr>
				<th>
					${message("admin.firmware.xml")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="file" name="firmwareFile"/>
					</span>
				</td>
			</tr>
			[#if checkResult??]
				<tr>
					<th>
						${message("admin.firmware.checkResult")}:
					</th>
					<td>
						${checkResult.content}
					</td>
				</tr>
			[/#if]
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="previewButton" class="button" value="${message("admin.common.preview")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.ehtml'" />
				</td>
			</tr>
		</table>
	</form>
	[#if firmwares?has_content]
	<form id="dataForm" action="save.ehtml" method="post">
		<table class="input">
			[#list firmwares as firmware]
				<tr>
					<th>
						${message("admin.firmware.detail")}:
					</th>
					<td>
						<table>
							<tr>
								<td>
									<input type="text" name="firmwares[${firmware_index}].productCategory.name"  value="${firmware.productCategory.name}" maxlength="200" style="width: 50px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].url"  value="${firmware.url}" maxlength="200" style="width: 250px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].ftpname"  value="${firmware.ftpname}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].ftppwd"  value="${firmware.ftppwd}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].version"  value="${firmware.version}" maxlength="200" style="width: 50px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].length"  value="${firmware.length}" maxlength="200" style="width: 50px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].md5"  value="${firmware.md5}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].date"  value="${firmware.date}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="firmwares[${firmware_index}].enddate"  value="${firmware.enddate}" maxlength="200" style="width: 100px;" readonly/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.type")}:
					</th>
					<td>
						<select name="firmwares[${firmware_index}].type">
							[#list types as t]
								<option value="${t}">${message("admin.firmware.type." + t)}</option>
							[/#list]
						</select>
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.hour")}:
					</th>
					<td>
						<select name="firmwares[${firmware_index}].hour">
							[#list hours as h]
								<option value="${h}">${h}</option>
							[/#list]
						</select>
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.area")}:
					</th>
					<td>
						<span class="fieldSet">
							<input type="hidden" id="areaId${firmware_index}" name="areaIds"/>
						</span>
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.targetVersion")}:
					</th>
					<td>
						<input type="text" name="firmwares[${firmware_index}].targetVersion" class="text" maxlength="200"  />
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.productSn")}:
					</th>
					<td>
						<input type="text" name="firmwares[${firmware_index}].productSn" class="text productSn" maxlength="200"  style="width: 400px;" />
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.firmware.product.type")}:
					</th>
					<td>
						<input type="radio" name="firmwares[${firmware_index}].productType" value="include" checked/>${message("admin.firmware.product.type.include")}
						<input type="radio" name="firmwares[${firmware_index}].productType" value="exclude" />${message("admin.firmware.product.type.exclude")}
					</td>
				</tr>
			[/#list]
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="saveButton" class="button" value="${message("admin.common.submit")}" />
				</td>
			</tr>
		</table>
	</form>
	[/#if]
</body>
</html>