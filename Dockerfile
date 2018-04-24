FROM python:3.6

ADD . /mmu
WORKDIR /mmu
RUN pip install -r requirements.txt
EXPOSE 5303