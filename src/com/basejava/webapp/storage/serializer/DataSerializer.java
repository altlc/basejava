package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class DataSerializer implements Serializer {

    @Override
    public void doPutFile(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeList(dos, r.getContacts().entrySet(), listItem -> {
                dos.writeUTF(listItem.getKey().name());
                dos.writeUTF(listItem.getValue());
            });

            writeList(dos, r.getSections().entrySet(), listItem -> {
                SectionType st = listItem.getKey();
                Section section = listItem.getValue();
                dos.writeUTF(st.name());

                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeList(dos, ((ListSection) section).getContentList(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeList(dos, ((OrganizationSection) section).getOrganisationsList(), organisation -> {
                            dos.writeUTF(organisation.getHomePage().getName());
                            dos.writeUTF(Objects.requireNonNullElse(organisation.getHomePage().getUrl(), ""));
                            writeList(dos, organisation.getStages(), stage -> {
                                writeDate(dos, stage.getStartDate());
                                writeDate(dos, stage.getEndDate());
                                dos.writeUTF(stage.getJobTitle());
                                dos.writeUTF(Objects.requireNonNullElse(stage.getDescription(), ""));
                            });
                        });
                        break;

                }
            });
        }
    }

    @Override
    public Resume doGetFile(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            mapList(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            mapList(dis, () -> {
                SectionType st = SectionType.valueOf(dis.readUTF());

                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(st, new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(st,
                                new OrganizationSection(readList(dis, () ->
                                        new Organisation(dis.readUTF(), emptyToNull(dis.readUTF()), readList(dis, () ->
                                                new Organisation.Stage(readDate(dis), readDate(dis), dis.readUTF(), emptyToNull(dis.readUTF())))
                                        ))));
                        break;

                }
            });
            return resume;
        }
    }

    private String emptyToNull(String text) {
        return text.equals("") ? null : text;
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private LocalDate readDate(DataInputStream dos) throws IOException {
        return LocalDate.of(dos.readInt(), dos.readInt(), dos.readInt());
    }

    private <Type> void writeList(DataOutputStream dos, Collection<Type> list, ListItemWriter<Type> writer) throws IOException {
        dos.writeInt(list.size());
        for (Type item : list) {
            writer.write(item);
        }
    }

    private <Type> ArrayList<Type> readList(DataInputStream dis, ListItemReader<Type> reader) throws IOException {
        ArrayList<Type> list = new ArrayList<>();
        mapList(dis, () -> list.add(reader.read()));
        return list;
    }

    private void mapList(DataInputStream dis, ListItemCallback callback) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            callback.call();
        }
    }

    public interface ListItemReader<Type> {
        Type read() throws IOException;
    }

    public interface ListItemWriter<Type> {
        void write(Type listItem) throws IOException;
    }

    public interface ListItemCallback {
        void call() throws IOException;
    }

}
