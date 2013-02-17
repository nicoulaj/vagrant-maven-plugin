> --------------------------------------------------------------------------------------
>
> **[Maven](http://maven.apache.org) plugin for [Vagrant](http://www.vagrantup.com).**
>
> --------------------------------------------------------------------------------------
>
> ![Maven](images/maven.png)&nbsp;&nbsp;**|**&nbsp;&nbsp;![Vagrant](images/vagrant.png)
> ======================================================================================
>
> --------------------------------------------------------------------------------------



##### Goals Overview


This plugin provides direct access to Vagrant commands:

<table style="width: 0%">
  <tr>
    <th style="text-align: right">Vagrant command</th>
    <th style="text-align: left">Maven goal</th>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant status</code></td>
    <td style="text-align: left"><a href="./status-mojo.html">vagrant:status</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant init</code></td>
    <td style="text-align: left"><a href="./init-mojo.html">vagrant:init</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant up</code></td>
    <td style="text-align: left"><a href="./up-mojo.html">vagrant:up</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant halt</code></td>
    <td style="text-align: left"><a href="./halt-mojo.html">vagrant:halt</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant destroy</code></td>
    <td style="text-align: left"><a href="./destroy-mojo.html">vagrant:destroy</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant package</code></td>
    <td style="text-align: left"><a href="./package-mojo.html">vagrant:package</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant provision</code></td>
    <td style="text-align: left"><a href="./provision-mojo.html">vagrant:provision</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant suspend</code></td>
    <td style="text-align: left"><a href="./suspend-mojo.html">vagrant:suspend</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant resume</code></td>
    <td style="text-align: left"><a href="./resume-mojo.html">vagrant:resume</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant reload</code></td>
    <td style="text-align: left"><a href="./reload-mojo.html">vagrant:reload</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant ssh</code></td>
    <td style="text-align: left"><a href="./ssh-mojo.html">vagrant:ssh</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant ssh-config</code></td>
    <td style="text-align: left"><a href="./ssh-config-mojo.html">vagrant:ssh-config</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant box add</code></td>
    <td style="text-align: left"><a href="./box-add-mojo.html">vagrant:box-add</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant box list</code></td>
    <td style="text-align: left"><a href="./box-list-mojo.html">vagrant:box-list</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant box remove</code></td>
    <td style="text-align: left"><a href="./box-remove-mojo.html">vagrant:box-remove</a></td>
  </tr>
  <tr>
    <td style="text-align: right"><code>vagrant box repackage</code></td>
    <td style="text-align: left"><a href="./box-repackage-mojo.html">vagrant:box-repackage</a></td>
  </tr>
</table>


##### Examples

* [Running a VM during integration tests](./examples/running-a-vm-during-integration-tests.html)
