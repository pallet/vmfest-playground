(use 'vmfest.manager)
(use '[vmfest.virtualbox.image :only [setup-model]])

;; First we need to define a connection to our VirtualBox host
;; service.
(def my-server (server "http://localhost:18083"))

;; We need an image model to play with. This will set up a fairly up-to-date
;; Debian image.
(setup-model "https://s3.amazonaws.com/vmfest-images/debian-6.0.2.1-64bit-v0.3.vdi.gz" my-server)
;; {:image-file "/var/folders...}

;; let's check that the image model has been installed
(models)
;; (:debian-6.0.2.1-64bit-v0.3) <-- you should see this

;; Time do create a VM instance. We'll call it my-vmfest-vm. This is
;; the name that will appear in VirtualBox's GUI.
(def my-machine (instance my-server "my-vmfest-vm" :debian-6.0.2.1-64bit-v0.3 :micro))

;; Notice that once we have created a VM we don't need to reference
;; the server anymore
(start my-machine)

;; Get the IP address of the machine. At this point, you can SSH into
;; this machine with user/password: vmfest/vmfest
(get-ip my-machine)

;; You can pause and resume the VM.
(pause my-machine)
(resume my-machine)

;; Stopping the VM will send a signal to the OS to shutdown. This will
;; not the VM itself, just the OS run by the VM
(stop my-machine)

;; This will turn off the VM completely and immediately.
(power-down my-machine)

;; Once we are done with this VM, we can destroy it, which will remove
;; any trace of it's existence. Your data will be lost, but not the
;; original image this VM was booted off.
(destroy my-machine)


;;; MULTIPLE INSTANCES

;; Now we are going to create multiple instances of the same image.
;; First we need some names for each instance. names will do just
;; that, e.g.: (names 3) -> ("vmfest-0" "vmfest-1" "vmfest-2").
(defn
  names [n]
  (map #(format "vmfest-%s" %) (range n)))

;; This function will create a debian instance based on the image
;; downloaded above
(defn deb-instance [server name]
  (instance server name :debian-6.0.2.1-64bit-v0.3 :micro))

;; Let's create a few images. Notice that in this case we're creating
;; 5. Each machine takes roughly 0.5GB of RAM, so change the number to
;; match your available RAM.
(def my-machines (pmap #(deb-instance my-server %)
                       (names 5)))

;; From here we can start, power-down, and destroy all the VMs in parallel.
(pmap start my-machines)
(pmap power-down my-machines)
(pmap destroy my-machines)