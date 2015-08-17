
package consul.model.health;

/**
 * Copyright © 2015 Lithium Technologies, Inc. All rights reserved subject to the terms of
 * the MIT License located at
 * <p/>
 * <p/>
 * LICENSE FILE DISCLOSURE STATEMENT AND COPYRIGHT NOTICE This LICENSE.txt file sets forth
 * the general licensing terms and attributions for the “Elasticsearch Consul Discovery
 * plugin” project software provided by Lithium Technologies, Inc. (“Lithium”).  This
 * software is based upon certain software files authored by Grant Rodgers in 2015 as part
 * of the “Elasticsearch SRV discovery plugin” project.  As a result, some of the files in
 * this Elasticsearch Consul Discovery plugin” project software are wholly authored by
 * Lithium, some are wholly authored by Grant Rodgers, and others are originally authored
 * by Grant Rodgers and subsequently modified by Lithium.  Files that were either modified
 * or wholly authored by Lithium contain an additional LICENSE.txt file indicating whether
 * they were modified or wholly authored by Lithium.  Any LICENSE.txt files, copyrights or
 * attribution originally included in the files of the original “Elasticsearch SRV
 * discovery plugin” remain unchanged. Copyright Notices The following copyright notice
 * applies to only those files and modifications authored by Lithium: Copyright © 2015
 * Lithium Technologies, Inc.  All rights reserved subject to the terms of the MIT License
 * below. The following copyright notice applies to only those files in the original
 * “Elasticsearch SRV discovery plugin” project software excluding any modifications by
 * Lithium: Copyright (c) 2015 Grant Rodgers License The following MIT License, as
 * originally presented in the “Elasticsearch SRV discovery plugin” project software, also
 * applies to files authored or modified by Lithium.  Your use of the “Elasticsearch
 * Consul Discovery plugin” project software is therefore subject to the terms of the
 * following MIT License.  Except as may be granted herein or by separate express written
 * agreement, this file provides no license to any Lithium patents, trademarks,
 * copyrights, or other intellectual property. MIT License Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions: The above
 * copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * <p/>
 * <p/>
 * <p/>
 * Created by Jigar Joshi on 8/9/15.
 */
public class Check {

	private String CheckID;
	private String Name;
	private String Node;
	private String Notes;
	private String Output;
	private String ServiceID;
	private String ServiceName;
	private String Status;

	public String getCheckID() {
		return CheckID;
	}

	public void setCheckID(String CheckID) {
		this.CheckID = CheckID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getNode() {
		return Node;
	}

	public void setNode(String Node) {
		this.Node = Node;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String Notes) {
		this.Notes = Notes;
	}

	public String getOutput() {
		return Output;
	}

	public void setOutput(String Output) {
		this.Output = Output;
	}

	public String getServiceID() {
		return ServiceID;
	}

	public void setServiceID(String ServiceID) {
		this.ServiceID = ServiceID;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String ServiceName) {
		this.ServiceName = ServiceName;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Check check = (Check) o;

		if (CheckID != null ? !CheckID.equals(check.CheckID) : check.CheckID != null)
			return false;
		if (Name != null ? !Name.equals(check.Name) : check.Name != null) return false;
		if (Node != null ? !Node.equals(check.Node) : check.Node != null) return false;
		if (Notes != null ? !Notes.equals(check.Notes) : check.Notes != null)
			return false;
		if (Output != null ? !Output.equals(check.Output) : check.Output != null)
			return false;
		if (ServiceID != null ? !ServiceID.equals(check.ServiceID) : check.ServiceID != null)
			return false;
		if (ServiceName != null ? !ServiceName.equals(check.ServiceName) : check.ServiceName != null)
			return false;
		if (Status != null ? !Status.equals(check.Status) : check.Status != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = CheckID != null ? CheckID.hashCode() : 0;
		result = 31 * result + (Name != null ? Name.hashCode() : 0);
		result = 31 * result + (Node != null ? Node.hashCode() : 0);
		result = 31 * result + (Notes != null ? Notes.hashCode() : 0);
		result = 31 * result + (Output != null ? Output.hashCode() : 0);
		result = 31 * result + (ServiceID != null ? ServiceID.hashCode() : 0);
		result = 31 * result + (ServiceName != null ? ServiceName.hashCode() : 0);
		result = 31 * result + (Status != null ? Status.hashCode() : 0);
		return result;
	}
}
