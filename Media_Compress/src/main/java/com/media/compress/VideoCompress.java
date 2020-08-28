package com.media.compress;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class VideoCompress {

	private static void compress() throws IOException {

		// FFmpeg ffmpeg = new FFmpeg("C:/Barkha/WTMS/AllJars/ffmpeg-20200628-4cfcfb3-win64-static/bin/ffmpeg");
		// FFprobe ffprobe = new FFprobe("C:/Barkha/WTMS/AllJars/ffmpeg-20200628-4cfcfb3-win64-static/bin/ffprobe");

		// Below is the code to read FFmpeg & FFprobe path from the properties file.i.e FFMPEG.properties
		Properties prop = new Properties();
		prop.load(VideoCompress.class.getClassLoader().getResourceAsStream("FFMPEG.properties"));
		FFmpeg ffmpeg = new FFmpeg(prop.getProperty("ffmpeg"));
		FFprobe ffprobe = new FFprobe(prop.getProperty("ffprobe"));

		FFmpegBuilder builder = new FFmpegBuilder().setInput("C:/Barkha/Image/VID_20190727_214545.mp4") // Filename, or a FFmpegProbeResult
				.overrideOutputFiles(true) // Override the output if it exists
				.addOutput("C:/Barkha/Image/VID_20190727_214545_output.mp4") // Filename for the destination
				.setFormat("mp4") // Format is inferred from filename, or can be set
				// .setTargetSize(250_000) // Aim for a 250KB file
				.disableSubtitle() // No subtiles
				.setAudioChannels(1) // Mono audio
				// .setAudioChannels(2)
				.setAudioCodec("aac") // using the aac codec
				.setAudioSampleRate(48000) // at 48KHz
				.setAudioBitRate(32768) // at 32 kbit/s
				// .setAudioBitRate(126000)
				.setVideoCodec("libx264") // Video using x264
				// .setVideoPreset("-c:v libx264 -preset ultrafast")
				.setPreset("ultrafast") // default speed is medium
				.setVideoFrameRate(24, 1) // at 24 frames per second
				.setVideoResolution(1280, 720) // at 640x480 resolution
				// .setVideoResolution(1024, 768)
				// .setVideoResolution(640, 480)
				.setVideoBitRate(762800).setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
				.done();
		final FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		// Run a one-pass encode
		executor.createJob(builder).run();
		// Or run a two-pass encode (which is better quality at the cost of being slower)
		// executor.createTwoPassJob(builder).run();
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
		try {
			compress();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf1.format(cal1.getTime()));
	}

}
