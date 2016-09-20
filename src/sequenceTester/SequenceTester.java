package sequenceTester;

import sequence.Node;
import sequence.Sequence;

import java.util.ArrayList;
import java.util.Scanner;

class SequenceTester {

    private static ArrayList<Sequence> playlists = new ArrayList<>();
    private static Sequence activeSequence;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        do{
            printMainMenu();
            option = scanner.nextInt();
            switch (option) {
                //Exit Program
                case 0:
                    System.exit(0);
                    break;
                //Create Playlist
                case 1:
                    printCreatePlaylistOptions();
                    option = scanner.nextInt();
                    executeCreatePlaylistOption(option);
                    break;
                //Edit Playlist
                case 2:
                    selectActiveSequence();
                    do {
                        printEditPlaylistOptions();
                        option = scanner.nextInt();
                        executeEditPlaylistOptions(option);
                    } while (option != 0);
                    break;
                //Play Playlist
                case 3:
                    selectActiveSequence();
                    do {
                        printPlayPlaylistOptions();
                        option = scanner.nextInt();
                        executePlayPlaylistOptions(option);
                    } while (option != 0);
                    break;
                default:
                    System.out.println("Error: please put in valid input");
                    break;
            }
        } while (true);
    }

    private static void printMainMenu() {
        System.out.println("Choose an option...");
        System.out.println("0. Press '0' to exit the program");
        System.out.println("1. Press '1' to create a new playlist");
        System.out.println("2. Press '2' to edit an existing playlist");
        System.out.println("3. Press '3' to play an existing playlist");
    }

    private static void printCreatePlaylistOptions() {
        System.out.println("0. Press '0' to return to the main menu");
        System.out.println("1. Press '1' to create an empty playlist");
        System.out.println("2. Press '2' to create a playlist with one song");
        System.out.println("3. Press '3' to create a new playlist from existing playlist(s)");
    }

    private static void selectActiveSequence() {
        System.out.println("Choose the playlist you wish to have active");
        printAllPlaylists();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        activeSequence = playlists.get(option);
    }

    private static void printEditPlaylistOptions() {
        System.out.println("0. Press '0' to return to the main menu");
        System.out.println("1. Press '1' to add a song to the playlist after the current location");
        System.out.println("2. Press '2' to add a song to the playlist before the current location");
        System.out.println("2. Press '3' to delete the current song from the playlist");
        System.out.println("3. Press '4' to append another playlist to the end of this playlist");
        System.out.println("5. Press '5' to move the current location up one");
        System.out.println("6. Press '6' to move the current location back one");
    }

    private static void printPlayPlaylistOptions() {
        System.out.println("0. Press '0' to return to the main menu");
        System.out.println("1. Press '1' to play the playlist from the beginning");
        System.out.println("2. Press '2' to advance the playlist");
        System.out.println("3. Press '3' to move the playlist back one");
    }

    private static void executeCreatePlaylistOption(int option) {
        Scanner optionScanner = new Scanner(System.in);
            switch (option) {
                case 0:
                    break;
                case 1:
                    playlists.add(playlists.size(), new Sequence());
                    break;
                case 2:
                    System.out.println("Please enter the ID of the song you wish to insert into your new playlist...");
                    int ID = optionScanner.nextInt();
                    playlists.add(playlists.size(), new Sequence(new Node(ID)));
                    System.out.println("The ID, " + ID + ", has been added to the new playlist.\n");
                    break;
                case 3:
                    System.out.println("Please choose a playlist to add to your new playlist");
                    printAllPlaylists();
                    int playlistIndex1 = optionScanner.nextInt();
                    System.out.println("Please choose a playlist to append to your new playlist");
                    printAllPlaylists();
                    int playlistIndex2 = optionScanner.nextInt();
                    Sequence combinedSequence = Sequence.combineSequence(playlists.get(playlistIndex1), playlists.get(playlistIndex2));
                    playlists.add(combinedSequence);
                    break;
                default:
                    System.out.println("Error: Please insert a valid input.");
            }
    }

    private static void executeEditPlaylistOptions(int option) {
        Scanner optionScanner = new Scanner(System.in);
        switch (option) {
            //Return to main menu
            case 0:
                break;
            //Add song to the playlist after the current position
            case 1:
                System.out.println("Add a song after " + activeSequence.getCurrent().getContent() + "? (y/n)...");
                String answer = optionScanner.nextLine();
                switch (answer) {
                    case "y":
                        System.out.println("Enter the ID of the inserted song...");
                        int ID = optionScanner.nextInt();
                        activeSequence.attach(ID);
                        System.out.println(ID + " has been attached to the playlist");
                        break;
                    case "n":
                        System.out.println("There were no additions to the playlist");
                        break;
                    default:
                        System.out.println("Error: Please insert a valid input");
                        break;
                }
                break;
            //Add song to the playlist before the current position
            case 2:
                System.out.println("Add a song before " + activeSequence.getCurrent().getContent() + "? (y/n)...");
                answer = optionScanner.nextLine();
                switch (answer) {
                    case "y":
                        System.out.println("Enter the ID of the inserted song...");
                        int ID = optionScanner.nextInt();
                        activeSequence.insert(ID);
                        System.out.println(ID + " has been inserted into the playlist");
                        break;
                    case "n":
                        System.out.println("There were no additions to the playlist");
                        break;
                    default:
                        System.out.println("Error: Please insert a valid input");
                        break;
                }
                break;
            //Delete the song at the current position
            case 3:
                System.out.println("Delete the song with ID " + activeSequence.getCurrent().getContent() + "? (y/n)");
                answer = optionScanner.nextLine();
                switch (answer) {
                    case "y":
                        int ID = activeSequence.getCurrent().getContent();
                        activeSequence.deleteCurrent();
                        System.out.println(ID + " has been inserted into the playlist");
                        break;
                    case "n":
                        System.out.println("Nothing was deleted form the playlist");
                        break;
                    default:
                        System.out.println("Error: Please insert a valid input");
                        break;
                }
                break;
            //Append another playlist to the end of this playlist
            case 4:
                System.out.println("Please choose the playlist you wish to append to the active playlist");
                printAllPlaylists();
                int playlistIndex = optionScanner.nextInt();
                activeSequence.appendSequence(playlists.get(playlistIndex));
                System.out.println("The playlists have been appended, the active playlist is now:");
                activeSequence.printAllContents();
                break;
            //Move the current location up one
            case 5:
                activeSequence.advance();
                System.out.println("The current location is now " + activeSequence.getCurrent().getContent());
                break;
            //Move the current location back one
            case 6:
                activeSequence.moveBack();
                System.out.println("The current location is now " + activeSequence.getCurrent().getContent());
                break;
            default:
                break;
        }
    }

    private static void executePlayPlaylistOptions(int option) {
        Scanner optionScanner = new Scanner(System.in);
        switch (option) {
            //Return to main menu
            case 0:
                break;
            //Play playlist from the beginning
            case 1:
                System.out.println("Starting the playlist");
                activeSequence.start();
                System.out.println("The current song is: " + activeSequence.getPlaying().getContent());
                break;
            //Advance the playlist one song
            case 2:
                activeSequence.playNext();
                System.out.println("Now playing: " + activeSequence.getPlaying().getContent());
                break;
            //Move playlist back one song
            case 3:
                activeSequence.playPrev();
                System.out.println("Now playing: " + activeSequence.getPlaying().getContent());
                break;
            default:
                System.out.println("Error: please insert valid input");
        }
    }

    private static void printAllPlaylists() {
        int index = 0;
        for(Sequence s : playlists) {
            System.out.println("Playlist at index " + index);
            s.printAllContents();
            index++;
        }
    }
}

